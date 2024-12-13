package org.example.taskscheduling.controllers;

import org.example.taskscheduling.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Endpoint to trigger notification sending
    @PostMapping("/send")
    public ResponseEntity<String> triggerNotifications() {
        notificationService.sendPendingTaskNotifications();
        return ResponseEntity.ok("Notifications sent successfully");
    }
}

