package com.example.sklep.order;


import com.example.sklep.product.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Map;

@AllArgsConstructor
@ToString
@Builder
@Getter
@EqualsAndHashCode
public final class OrderDTO {
    private final Map<ProductDTO, Integer> productToNumberMap;
    private final BigDecimal summaryPrice;
}
