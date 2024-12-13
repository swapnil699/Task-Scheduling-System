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
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
    }
}


