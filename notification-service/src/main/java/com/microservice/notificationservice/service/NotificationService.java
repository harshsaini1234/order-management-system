package com.microservice.notificationservice.service;

import com.microservice.notificationservice.dto.NotificationRequest;
import com.microservice.notificationservice.dto.NotificationResponse;
import com.microservice.notificationservice.entity.Notification;
import com.microservice.notificationservice.enums.NotificationStatus;
import com.microservice.notificationservice.exception.NotificationNotFoundException;
import com.microservice.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Transactional
    public NotificationResponse createNotification(NotificationRequest request){
        Notification notification = new Notification();
        notification.setUserId(request.userId());
        notification.setTitle(request.title());
        notification.setMessage(request.message());
        notification.setType(request.type());
        notification.setStatus(NotificationStatus.UNREAD);
        Notification savedNotification = notificationRepository.save(notification);
        return mapToResponse(savedNotification);

    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationByUserId(Long userId){
        return notificationRepository.
                findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    public List<NotificationResponse> getUnreadNotifications(Long userId){
        return notificationRepository.
                findByUserIdAndStatusOrderByCreatedAtDesc(userId,NotificationStatus.UNREAD)
                .stream()
                .map(this::mapToResponse)
                .toList();

    }
    @Transactional
    public NotificationResponse markAsRead(Long notificationId){
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(()->
                        new NotificationNotFoundException("Notification not found with Id : "+notificationId) );
        if (notification.getStatus()== NotificationStatus.UNREAD){
            notification.setStatus(NotificationStatus.READ);
            notification.setReadAt(LocalDateTime.now());

        }
        Notification updateNotification = notificationRepository.save(notification);
        return mapToResponse(updateNotification);

    }

    private NotificationResponse mapToResponse(Notification notification){
        return new NotificationResponse(
                notification.getId(),
                notification.getUserId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getType(),
                notification.getStatus(),
                notification.getCreatedAt(),
                notification.getReadAt()
        );
    }
}
