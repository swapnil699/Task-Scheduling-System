package org.example.taskscheduling.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog extends BaseModel {

    private String action;

    private String performedBy;

    private LocalDateTime timestamp;

    @Column(length = 1000)
    private String details;
}
