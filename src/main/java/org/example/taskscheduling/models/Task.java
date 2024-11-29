package org.example.taskscheduling.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Task extends BaseModel {

    private String title;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    private Status status; // Enum for status

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Notification> notifications;
}
