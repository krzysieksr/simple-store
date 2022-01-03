package com.example.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public final class OrderKafka {
    private final Map<Integer, Integer> productIdToNumberMap;
    private final BigDecimal summaryPrice;

}
