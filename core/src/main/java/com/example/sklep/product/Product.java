package com.example.sklep.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;


@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
public final class Product {
    private final int id;
    private final String name;
    private final BigDecimal priceUSD;
}
