package org.example.taskscheduling.services;

import org.example.taskscheduling.dtos.TaskCreationDTO;
import org.example.taskscheduling.exceptions.ProjectNotFoundException;
import org.example.taskscheduling.exceptions.TaskNotFoundException;
import org.example.taskscheduling.exceptions.UserNotFoundException;
import org.example.taskscheduling.models.*;
import org.example.taskscheduling.repositorys.ProjectRepository;
import org.example.taskscheduling.repositorys.TaskRepository;
import org.example.taskscheduling.repositorys.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    TaskServiceImpl(ProjectRepository projectRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Task createTask(TaskCreationDTO task) throws UserNotFoundException, ProjectNotFoundException {
        Task newTask = new Task();

        // Retrieve the user and project
        User assignee = userRepository.findById(task.getAssignee().getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        // Check if the user's role is MANAGER
        if (assignee.getRoles() != Roles.MANEGER) {
            throw new IllegalArgumentException("Only users with the MANAGER role can be assigned tasks.");
        }
        Project project = projectRepository.findById(task.getProject().getId())
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        // Set basic task details
        newTask.setAssignee(assignee);
        newTask.setProject(project);
        newTask.setDescription(task.getDescription());
        newTask.setTitle(task.getTitle());

        // Save the task
        taskRepository.save(newTask);

        return newTask;
    }

    @Override
    public Task getTaskById(Long id) throws TaskNotFoundException {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public void deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Task not found with ID: " + id);
        }
    }

    @Override
    public void assignTaskToUser(Long taskId, Long userId) throws UserNotFoundException, TaskNotFoundException {
        // Retrieve the user from the repository
        User assignee = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        // Retrieve the task from the repository
        Task taskToAssign = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + taskId + " not found"));

        // Assign the user to the task
        taskToAssign.setAssignee(assignee);

        // Save the updated task to persist changes
        taskRepository.save(taskToAssign);
    }
    @Override
    public Task updateTask(Long id, Task updatedTask) throws TaskNotFoundException {
        // Fetch the existing task
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found"));

        // Update task fields
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDeadline(updatedTask.getDeadline());
        existingTask.setProject(updatedTask.getProject());
        existingTask.setAssignee(updatedTask.getAssignee());

        // Save the updated task
        return taskRepository.save(existingTask);
    }

    @Override
    public Task updateTaskStatus(Long taskId, String status) throws TaskNotFoundException, IllegalArgumentException {
        // Fetch the existing task
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + taskId + " not found"));

        // Validate and update the status
        try {
            task.setStatus(Status.valueOf(status)); // Convert the string to the Status enum
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status); // Throw exception for invalid status
        }

        // Save the updated task
        return taskRepository.save(task);
    }

    @Override
    public Page<Task> getTasksByProjectIdWithPagination(Long projectId, Pageable pageable) {
        return taskRepository.findByProjectId(projectId, pageable);
    }

}


