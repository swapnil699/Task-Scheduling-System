package org.example.taskscheduling.services;
import org.example.taskscheduling.dtos.ProjectCreationDTO;
import org.example.taskscheduling.models.Project;
import org.example.taskscheduling.models.Status;
import org.example.taskscheduling.models.Team;

import org.example.taskscheduling.repositorys.ProjectRepository;
import org.example.taskscheduling.repositorys.TeamRepository;
import org.example.taskscheduling.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public Project createProject(ProjectCreationDTO projectDto) {
        Team team = teamRepository.findById(projectDto.getTeamId())
                .orElseThrow(() -> new IllegalArgumentException("Team not found with ID: " + projectDto.getTeamId()));

        Project project = new Project();
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setTeam(team);
        project.setCreatedDate(LocalDateTime.now());
        project.setTasks(List.of());

        return projectRepository.save(project);
    }
    @Override
    public Status getProjectStatus(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + projectId));

        // Assume the status is derived based on task statuses or another logic
        return deriveProjectStatus(project);
    }

    @Override
    public Map<Long, Status> getAllProjectStatuses() {
        List<Project> projects = projectRepository.findAll();
        Map<Long, Status> projectStatuses = new HashMap<>();

        for (Project project : projects) {
            projectStatuses.put(project.getId(), deriveProjectStatus(project));
        }

        return projectStatuses;
    }
    private Status deriveProjectStatus(Project project) {
        // Sample logic to derive status
        if (project.getTasks() == null || project.getTasks().isEmpty()) {
            return Status.PENDING;
        }

        boolean allCompleted = project.getTasks().stream().allMatch(task -> task.getStatus() == Status.COMPLETED);
        if (allCompleted) {
            return Status.COMPLETED;
        }

        boolean anyInProgress = project.getTasks().stream().anyMatch(task -> task.getStatus() == Status.IN_PROGRESS);
        if (anyInProgress) {
            return Status.IN_PROGRESS;
        }

        return Status.PENDING;
    }
}
