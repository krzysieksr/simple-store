package com.example.sklep.cart;

import com.example.common.ProductNotFoundException;
import com.example.common.TooFewProductAvailableException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class WarehouseAdapter implements Warehouse {

    @Override
    public Mono<Integer> getAmountByProductId(int productId) {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8081")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return webClient.get()
                .uri("/warehouse/" + productId)
                .retrieve()
                .bodyToFlux(Integer.class)
                .next();
    }

    @Override
    public void checkIfAvailable(int productId, int expectedNewProductAmount) {
        Optional<Integer> productAmountOptional = getAmountByProductId(productId)
                .blockOptional();

        Integer productAmount = productAmountOptional.orElseThrow(() ->
                new ProductNotFoundException(ProductNotFoundException.PRODUCT_MESSAGE, productId));

        if (productAmount < expectedNewProductAmount) {
            throw new TooFewProductAvailableException(productAmount, expectedNewProductAmount);
        }
    }
}
