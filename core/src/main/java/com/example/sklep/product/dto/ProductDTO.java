package com.example.sklep.product.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Builder
public final class ProductDTO {
    private final int id;
    private final String name;
    private final BigDecimal priceUSD;
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }
}
