package com.example.sklep.order;

import com.example.common.OrderKafka;
import com.example.common.ProductNotFoundException;
import com.example.common.TooFewProductAvailableException;
import com.example.sklep.cart.Cart;
import com.example.sklep.cart.Warehouse;
import com.example.sklep.product.Product;
import com.example.sklep.product.converters.ProductToProductDTOConv;
import com.example.sklep.product.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final ProductToProductDTOConv productToProductDTOConv;
    private final Warehouse warehouse;
    private final OrderProducer orderProducer;

    public OrderService(ProductToProductDTOConv productToProductDTOConv, Warehouse warehouse, OrderProducer orderProducer) {
        this.productToProductDTOConv = productToProductDTOConv;
        this.warehouse = warehouse;
        this.orderProducer = orderProducer;
    }


    public OrderDTO calculateOrder(Cart cardForCustomer) {
        Map<Product, Integer> productAmountMap = cardForCustomer.getProductAmountMap();

        Map<ProductDTO, Integer> productToNumberMap =
                productAmountMap.entrySet().stream()
                        .collect(Collectors.toMap(
                                e -> productToProductDTOConv.convert(e.getKey()),
                                e -> e.getValue()
                        ));

        BigDecimal summaryPrice = new BigDecimal(0);
        for (Map.Entry<Product, Integer> productAmountEntry : productAmountMap.entrySet()) {
            summaryPrice = summaryPrice.add(productAmountEntry.getKey().getPriceUSD()
                    .multiply(new BigDecimal(productAmountEntry.getValue())));
        }

        //TODO Dodac OrderDTO do Mapy
        return OrderDTO.builder().productToNumberMap(productToNumberMap).summaryPrice(summaryPrice).build();
    }

    public OrderDTO makeOrder(Cart cardForCustomer) {
        Map<Product, Integer> productAmountMap = cardForCustomer.getProductAmountMap();

        Map<ProductDTO, Integer> productToNumberMap =
                productAmountMap.entrySet().stream()
                        .map(e -> {
                            ProductDTO productDTO = productToProductDTOConv.convert(e.getKey());
                            try {
                                warehouse.checkIfAvailable(e.getKey().getId(), e.getValue());
                            } catch (TooFewProductAvailableException | ProductNotFoundException ex) {
                                productDTO.setMessage(ex.getMessage());
                                return new AbstractMap.SimpleEntry<>(productDTO, 0);
                            }
                            return new AbstractMap.SimpleEntry<>(productDTO, e.getValue());
                        })
                        .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey,
                                AbstractMap.SimpleEntry::getValue));

        BigDecimal summaryPrice = new BigDecimal(0);
        for (Map.Entry<Product, Integer> productAmountEntry : productAmountMap.entrySet()) {
            summaryPrice = summaryPrice.add(productAmountEntry.getKey().getPriceUSD()
                    .multiply(new BigDecimal(productAmountEntry.getValue())));
        }

        Map<Integer, Integer> prodIdToAmount = productToNumberMap.entrySet().stream()
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey().getId(), e.getValue()))
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue));
        orderProducer.send(OrderKafka.builder()
                .productIdToNumberMap(prodIdToAmount)
                .summaryPrice(summaryPrice).build());

        return OrderDTO.builder().productToNumberMap(productToNumberMap).summaryPrice(summaryPrice).build();
    }
}
