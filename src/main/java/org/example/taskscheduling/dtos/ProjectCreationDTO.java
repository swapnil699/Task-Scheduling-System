package org.example.taskscheduling.dtos;

import lombok.Data;

@Data
public class ProjectCreationDTO {
    private String name;
    private String description;
    private Long teamId;
}
