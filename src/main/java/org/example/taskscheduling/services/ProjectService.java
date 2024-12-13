package org.example.taskscheduling.services;

import org.example.taskscheduling.dtos.ProjectCreationDTO;
import org.example.taskscheduling.models.Project;
import org.example.taskscheduling.models.Status;

import java.util.Map;

public interface ProjectService {
    Project createProject(ProjectCreationDTO projectDto);

    Status getProjectStatus(Long projectId);

    Map<Long, Status> getAllProjectStatuses();
}
