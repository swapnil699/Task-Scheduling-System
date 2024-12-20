package org.example.taskscheduling.controllers;

import org.example.taskscheduling.dtos.TaskCreationDTO;
import org.example.taskscheduling.exceptions.ProjectNotFoundException;
import org.example.taskscheduling.exceptions.TaskNotFoundException;
import org.example.taskscheduling.exceptions.UserNotFoundException;
import org.example.taskscheduling.models.Pokemon;
import org.example.taskscheduling.models.Task;
import org.example.taskscheduling.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    // Create a new task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskCreationDTO task) {
        try {
            Task createdTask = taskService.createTask(task);
            return ResponseEntity.ok(createdTask);  // Return the created task
        } catch (UserNotFoundException | ProjectNotFoundException e) {
            return ResponseEntity.badRequest().body(null);  // Return a 400 if user or project is not found
        }
    }

    // Get all tasks
    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    // Get task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        try {
            Task task = taskService.getTaskById(id);  // Returns Task, not Optional
            return ResponseEntity.ok(task);
        } catch (TaskNotFoundException ex) {
            return ResponseEntity.notFound().build();  // Return 404 if task not found
        }
    }

    // Delete a task
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted successfully");
    }

    // Assign a task to a user
    @PostMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<String> assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId) throws UserNotFoundException, TaskNotFoundException {
        taskService.assignTaskToUser(taskId, userId);
        return ResponseEntity.ok("Task assigned successfully");
    }
    // Update a task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        try {
            Task task = taskService.updateTask(id, updatedTask);
            return ResponseEntity.ok(task); // Return the updated task
        } catch (TaskNotFoundException e) {
            return ResponseEntity.notFound().build(); // Return 404 if task not found
        }
    }

    // Update task status
    @PatchMapping("/{taskId}/status")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Long taskId, @RequestParam String status) {
        try {
            Task updatedTask = taskService.updateTaskStatus(taskId, status.toUpperCase()); // Normalize status to uppercase
            return ResponseEntity.ok(updatedTask); // Return the task with updated status
        } catch (TaskNotFoundException e) {
            return ResponseEntity.notFound().build(); // Return 404 if task not found
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Return 400 if invalid status
        }
    }
    //pagination http://localhost:8080/tasks/project/1/paginated?page=0&size=5
    @GetMapping("/project/{projectId}/paginated")
    public ResponseEntity<Page<Task>> getTasksByProjectWithPagination(
            @PathVariable Long projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasks = taskService.getTasksByProjectIdWithPagination(projectId, pageable);
        return ResponseEntity.ok(tasks);
    }

    // pagination + soring GET http://localhost:8080/tasks/project/1/paginated-sorted?page=0&size=5&sortBy=title
    @GetMapping("/project/{projectId}/paginated-sorted")
    public ResponseEntity<Page<Task>> getTasksByProjectWithPaginationAndSorting(
            @PathVariable Long projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {

        List<String> validSortFields = List.of("title", "description", "deadline", "status");
        if (!validSortFields.contains(sortBy)) {
            sortBy = "title"; // Default sorting field
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));
        Page<Task> tasks = taskService.getTasksByProjectIdWithPaginationAndSorting(projectId, pageable);
        return ResponseEntity.ok(tasks);
    }

    //task reward
    @PostMapping("/{taskId}/complete")
    public ResponseEntity<Pokemon> completeTask(@PathVariable Long taskId, @RequestParam Long userId) {
        try {
            taskService.completeTask(taskId, userId);
            String randomPokemonName = taskService.getRandomPokemonName();
            Pokemon rewardedPokemon = taskService.rewardUserWithPokemon(randomPokemonName);
            return ResponseEntity.ok(rewardedPokemon);
        } catch (TaskNotFoundException | UserNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
