package com.example.common;

public class ProductNotFoundException extends RuntimeException {

    public static final String WAREHOUSE_MESSAGE = "Product with id %s not found in warehouse.";
    public static final String PRODUCT_MESSAGE = "Product with id %s not found in catalog.";

    public ProductNotFoundException(String message, int productId) {
        super(String.format(message, productId));
    }
}
