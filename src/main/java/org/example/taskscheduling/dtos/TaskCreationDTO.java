package org.example.taskscheduling.dtos;

import lombok.Data;
import org.example.taskscheduling.models.Project;
import org.example.taskscheduling.models.User;

@Data
public class TaskCreationDTO {
    private String title;
    private String description;
    private User assignee;
    private Project project;
}
