package com.microservice.productservices.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String sku,
        String description,
        BigDecimal price,
        Integer stockQuantity
) {
}
