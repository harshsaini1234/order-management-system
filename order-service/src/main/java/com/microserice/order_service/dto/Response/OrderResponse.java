package com.microserice.order_service.dto.Response;

import com.microserice.order_service.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        Long userId,
        List<OrderItemResponse> items,
        BigDecimal totalAmount,
        OrderStatus status,
        LocalDateTime createAt
) {
}
