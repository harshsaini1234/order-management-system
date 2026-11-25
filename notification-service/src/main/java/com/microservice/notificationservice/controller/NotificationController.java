package com.microservice.notificationservice.controller;

import com.microservice.notificationservice.dto.NotificationRequest;
import com.microservice.notificationservice.dto.NotificationResponse;
import com.microservice.notificationservice.entity.Notification;
import com.microservice.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public NotificationResponse createNotificationRequest(@Valid @RequestBody NotificationRequest request){
        return notificationService.createNotification(request);

    }

    @GetMapping("user/{userId}")
    public List<NotificationResponse> getNotificationByUserId(@PathVariable Long userId ){
        return notificationService.getNotificationByUserId(userId);
    }

    @GetMapping("/user/{userId}/unread")
    public List<NotificationResponse> getUnreadNotification(@PathVariable Long userId){
        return notificationService.getUnreadNotifications(userId);
    }
    @PatchMapping("/{id}/read")
    public NotificationResponse markAsRead(@PathVariable Long id){
        return notificationService.markAsRead(id);
    }
}
