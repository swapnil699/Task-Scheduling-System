package org.example.taskscheduling.controllers;

import org.example.taskscheduling.dtos.ProjectCreationDTO;
import org.example.taskscheduling.models.Project;
import org.example.taskscheduling.models.Status;
import org.example.taskscheduling.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Create a project
    @PostMapping("/create")
    public ResponseEntity<Project> createProject(@RequestBody ProjectCreationDTO projectDto) {
        Project createdProject = projectService.createProject(projectDto);
        return ResponseEntity.ok(createdProject);
    }

    // Get the status of a specific project by ID
    @GetMapping("/{id}/status")
    public ResponseEntity<Status> getProjectStatus(@PathVariable Long id) {
        Status status = projectService.getProjectStatus(id);
        return ResponseEntity.ok(status);
    }

    // Get the statuses of all projects
    @GetMapping("/statuses")
    public ResponseEntity<Map<Long, Status>> getAllProjectStatuses() {
        Map<Long, Status> statuses = projectService.getAllProjectStatuses();
        return ResponseEntity.ok(statuses);
    }
}

// done