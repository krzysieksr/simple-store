package com.example.sklep.cart;


import com.example.sklep.product.Product;
import com.example.sklep.product.ProductFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CartFacade {
    Logger logger = LogManager.getLogger(this);

    private final Warehouse warehouse;
    private final CartService cartService;
    private final ProductFacade productFacade;

    public CartFacade(Warehouse warehouse, CartService cartService, ProductFacade productFacade) {
        this.warehouse = warehouse;
        this.cartService = cartService;
        this.productFacade = productFacade;
    }

    public void addProductToCart(int customerId, ProductAddedToCartDTO productAddedToCartDTO) throws CartNotFoundException {
        Cart customerCart = cartService.getCartOrThrow(customerId);

        int productId = productAddedToCartDTO.getProductId();
        int productAmount = productAddedToCartDTO.getAmount();

        Optional<Product> productByIdOptional = customerCart.getProductById(productId);
        Product product = productByIdOptional.orElseGet(() -> productFacade.getProductById(productId));

        warehouse.checkIfAvailable(productId, productAmount);

        cartService.setProductToCart(product, productAmount, customerId);
    }

    public void createCart(Integer customerId) {
        cartService.createCart(customerId);
        logger.info("Created card for customer {}.", customerId);
    }

    public Cart getCartOrThrow(int customerId) {
        return cartService.getCartOrThrow(customerId);
    }
}

//        Product product = productByIdOptional.orElseGet(new Supplier<Product>() {
//            @Override
//            public Product get() {
//                return productFacade.getProductById(productId);
//            }
//        });
