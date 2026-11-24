package com.microservice.paymentservice.dto;

import com.microservice.paymentservice.enums.PaymentMethod;
import com.microservice.paymentservice.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        Long id,
        Long orderId,
        Long userId,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        PaymentStatus status,
        LocalDateTime createAt
) {
}
