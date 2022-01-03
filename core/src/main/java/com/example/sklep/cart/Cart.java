package com.example.sklep.cart;

import com.example.sklep.product.Product;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class Cart {
    private final Map<Product, Integer> productAmountMap = new ConcurrentHashMap<>();

    public synchronized void pushProduct(Product product, int amountOfProduct) {
        productAmountMap.computeIfPresent(product, (productCurr, currAmount) -> currAmount += amountOfProduct);
        productAmountMap.putIfAbsent(product, amountOfProduct);
    }

    public int getAmountByProductInCart(Product product) {
        Integer amountInCart = productAmountMap.get(product);
        if (amountInCart == null) {
            return 0;
        }
        return amountInCart;
    }

    public Optional<Product> getProductById(int productId) {
        return productAmountMap.keySet()
                .stream().filter(product -> product.getId() == productId)
                .findFirst();
    }

    public Map<Product, Integer> getProductAmountMap() {
        //TODO Czy robic głębokie kopiowanie Mapy???
        return Collections.unmodifiableMap(productAmountMap);
    }
}
