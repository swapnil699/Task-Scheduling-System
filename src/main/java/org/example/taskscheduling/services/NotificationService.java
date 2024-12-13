package org.example.taskscheduling.services;

import org.example.taskscheduling.models.Notification;

public interface NotificationService {
    void sendPendingTaskNotifications();
    void sendNotification(Notification notification);
}
