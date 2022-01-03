package com.example.sklep.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
public class ProductDetails {
    private final int id;
    private final String description;
    private final String imagesPaths;
}
