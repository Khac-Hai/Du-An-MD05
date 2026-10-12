package com.omnichannel.notification.controller;

import com.omnichannel.notification.entity.NotificationLog;
import com.omnichannel.notification.repository.NotificationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;

    public NotificationController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @GetMapping
    public ResponseEntity<?> getLatestNotifications(@RequestParam(required = false) String email) {
        List<NotificationLog> list;
        if (email != null && !email.isBlank()) {
            list = notificationRepository.findByRecipientEmailOrderByCreatedAtDesc(email);
        } else {
            list = notificationRepository.findTop50ByOrderByCreatedAtDesc();
        }
        return ResponseEntity.ok(Map.of(
            "success", true,
            "count", list.size(),
            "data", list
        ));
    }

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(Map.of(
            "service", "notification-service",
            "status", "UP",
            "timestamp", System.currentTimeMillis()
        ));
    }
}
