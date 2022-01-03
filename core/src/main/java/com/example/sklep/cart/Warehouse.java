package com.example.sklep.cart;

import reactor.core.publisher.Mono;

public interface Warehouse {
    Mono<Integer> getAmountByProductId(int productId);

    void checkIfAvailable(int productId, int expectedNewProductAmount);
}
