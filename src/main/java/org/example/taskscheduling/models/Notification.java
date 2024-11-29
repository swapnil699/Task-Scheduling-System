package org.example.taskscheduling.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Notification extends BaseModel {

    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User recipient;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private LocalDateTime sentAt;
}
