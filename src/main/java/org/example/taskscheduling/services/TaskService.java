package org.example.taskscheduling.services;

import org.example.taskscheduling.dtos.TaskCreationDTO;
import org.example.taskscheduling.exceptions.ProjectNotFoundException;
import org.example.taskscheduling.exceptions.TaskNotFoundException;
import org.example.taskscheduling.exceptions.UserNotFoundException;
import org.example.taskscheduling.models.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TaskService {
    public Task createTask(TaskCreationDTO task) throws UserNotFoundException, ProjectNotFoundException;

    Task getTaskById(Long id) throws TaskNotFoundException;

    List<Task> getAllTasks();

    void deleteTask(Long id);
}
