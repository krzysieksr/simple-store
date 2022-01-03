package com.example.sklep.product.converters;

import com.example.sklep.product.Product;
import com.example.sklep.product.dto.ProductDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductToProductDTOConv implements Converter<Product, ProductDTO> {
    @Override
    public ProductDTO convert(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .priceUSD(product.getPriceUSD())
                .build();
    }
}
