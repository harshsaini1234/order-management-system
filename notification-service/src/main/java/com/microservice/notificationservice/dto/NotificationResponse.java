package com.microservice.notificationservice.dto;

import com.microservice.notificationservice.entity.Notification;
import com.microservice.notificationservice.enums.NotificationStatus;
import com.microservice.notificationservice.enums.NotificationType;

import java.time.LocalDateTime;

public record NotificationResponse(
        Long id,
        Long userId,
        String title,
        String message,
        NotificationType type,
        NotificationStatus status,
        LocalDateTime createdAt,
        LocalDateTime readAt
) {
}
