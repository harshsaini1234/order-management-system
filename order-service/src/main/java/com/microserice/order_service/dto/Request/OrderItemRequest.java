package com.microserice.order_service.dto.Request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderItemRequest(
        @NotNull(message = "Product ID is required")
        @Positive(message = "Product ID must be positive")
        Long productId,

        @NotNull(message = "Product quantity is required")
        @Positive(message = "Product quantity mus be positive")
        Integer quantity,

        @NotNull(message = "Unit price is required")
        @DecimalMin(value = "0.01",message = "Unit price must be greater than zero")
        @Digits(integer = 10,fraction = 2,
        message = "Unit price must have up to 10 integer and 2 decimal places ")
        BigDecimal unitPrice


) {

}
