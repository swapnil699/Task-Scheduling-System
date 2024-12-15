package org.example.taskscheduling.services;

import org.example.taskscheduling.dtos.TaskCreationDTO;
import org.example.taskscheduling.exceptions.ProjectNotFoundException;
import org.example.taskscheduling.exceptions.TaskNotFoundException;
import org.example.taskscheduling.exceptions.UserNotFoundException;
import org.example.taskscheduling.models.Project;
import org.example.taskscheduling.models.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

@Service
public interface TaskService {
    public Task createTask(TaskCreationDTO task) throws UserNotFoundException, ProjectNotFoundException;

    Task getTaskById(Long id) throws TaskNotFoundException;

    List<Task> getAllTasks();

    void deleteTask(Long id);

    void assignTaskToUser(Long taskId, Long userId) throws UserNotFoundException, TaskNotFoundException;
    Task updateTask(Long id, Task updatedTask) throws TaskNotFoundException;

    Task updateTaskStatus(Long taskId, String status) throws TaskNotFoundException, IllegalArgumentException;

    Page<Task> getTasksByProjectIdWithPagination(Long projectId, Pageable pageable);
    Page<Task> getTasksByProjectIdWithPaginationAndSorting(Long projectId, Pageable pageable);

}
