package com.microservice.paymentservice.dto;

import com.microservice.paymentservice.enums.PaymentMethod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentRequest(
        @NotNull
        Long orderId,

        @NotNull
        Long userId,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal amount,

        @NotNull
        PaymentMethod paymentMethod
) {
}
