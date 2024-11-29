package org.example.taskscheduling.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class AuditLog extends BaseModel {

    private String action;

    private String performedBy;

    private LocalDateTime timestamp;

    @Column(length = 1000)
    private String details;
}
