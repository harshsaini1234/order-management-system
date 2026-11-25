package com.microservice.notificationservice.dto;

import com.microservice.notificationservice.entity.Notification;
import com.microservice.notificationservice.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NotificationRequest(
        @NotNull(message = "User Id is required")
        Long userId,

        @NotBlank(message = "Title is required")
        @Size(max = 150)
        String title,

        @NotBlank(message = "Message is required")
        @Size(max = 1000)
        String message,

        @NotNull(message = "Notification Type is required")
        NotificationType type

) {
}
