package org.example.taskscheduling.services;

import org.example.taskscheduling.models.*;

import org.example.taskscheduling.repositorys.AuditLogRepository;
import org.example.taskscheduling.repositorys.NotificationRepository;
import org.example.taskscheduling.repositorys.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    @Scheduled(cron = "0 0 9 * * ?", zone = "UTC") // Run at 9 AM UTC
    @Scheduled(cron = "0 0 18 * * ?", zone = "UTC") // Run at 6 PM UTC
    public void sendPendingTaskNotifications() {
        // Fetch tasks with status 'PENDING'
        List<Task> pendingTasks = taskRepository.findByStatus(Status.PENDING);

        for (Task task : pendingTasks) {
            User assignee = task.getAssignee();
            if (assignee != null) {
                Notification notification = new Notification();
                notification.setMessage("Reminder: Your task \"" + task.getTitle() + "\" is still pending.");
                notification.setRecipient(assignee);
                notification.setTask(task);
                notification.setSentAt(LocalDateTime.now());

                // Save notification to database
                notificationRepository.save(notification);

                // Send notification asynchronously
                sendNotification(notification);

                // Log the action
                logNotification(notification);
            }
        }
    }

    @Override
    @Async
    public void sendNotification(Notification notification) {
        // Simulate sending a notification (e.g., via email or messaging service)
        System.out.println("Sending notification to " + notification.getRecipient().getName() +
                ": " + notification.getMessage());
    }

    private void logNotification(Notification notification) {
        auditLogRepository.save(new AuditLog(
                "Notification Sent",
                notification.getRecipient().getName(),
                LocalDateTime.now(),
                "Task: " + notification.getTask().getTitle() + ", Message: " + notification.getMessage()
        ));
    }
}
