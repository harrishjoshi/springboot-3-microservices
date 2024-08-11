package com.harrishjoshi.microservices.order.dto;

import java.math.BigDecimal;

public record OrderRequest(
        String skuCode,
        Integer quantity,
        BigDecimal price
) {
}
