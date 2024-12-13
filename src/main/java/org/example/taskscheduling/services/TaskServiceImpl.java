package org.example.taskscheduling.services;

import org.example.taskscheduling.dtos.TaskCreationDTO;
import org.example.taskscheduling.exceptions.ProjectNotFoundException;
import org.example.taskscheduling.exceptions.TaskNotFoundException;
import org.example.taskscheduling.exceptions.UserNotFoundException;
import org.example.taskscheduling.models.Project;
import org.example.taskscheduling.models.Task;
import org.example.taskscheduling.models.User;
import org.example.taskscheduling.repositorys.ProjectRepository;
import org.example.taskscheduling.repositorys.TaskRepository;
import org.example.taskscheduling.repositorys.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    // Constructor injection for repositories
    TaskServiceImpl(ProjectRepository projectRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Task createTask(TaskCreationDTO task) throws UserNotFoundException, ProjectNotFoundException {
        // Initialize a new Task object
        Task newTask = new Task();

        // Retrieve the user from the repository using the ID from the DTO
        Optional<User> userOp = userRepository.findById(task.getAssignee().getId());
        if (!userOp.isPresent()) {
            throw new UserNotFoundException("User not found");
        }

        // Retrieve the project from the repository using the ID from the DTO
        Optional<Project> projectOp = projectRepository.findById(task.getProject().getId());
        if (!projectOp.isPresent()) {
            throw new ProjectNotFoundException("Project not found");
        }

        // Set the assignee and project of the task
        newTask.setAssignee(userOp.get());
        newTask.setProject(projectOp.get());
        newTask.setDescription(task.getDescription());
        newTask.setTitle(task.getTitle());

        // Save the new task to the repository
        taskRepository.save(newTask);

        return newTask;
    }

    @Override
    public Task getTaskById(Long id) throws TaskNotFoundException {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }
}
