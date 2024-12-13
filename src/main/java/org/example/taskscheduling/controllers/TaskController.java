package org.example.taskscheduling.controllers;

import org.example.taskscheduling.dtos.TaskCreationDTO;
import org.example.taskscheduling.exceptions.ProjectNotFoundException;
import org.example.taskscheduling.exceptions.TaskNotFoundException;
import org.example.taskscheduling.exceptions.UserNotFoundException;
import org.example.taskscheduling.models.Task;
import org.example.taskscheduling.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
//
//    // Update a task
//    @PutMapping("/{id}")
//    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
//        Task task = taskService.updateTask(id, updatedTask);
//        return ResponseEntity.ok(task);
//    }
//
    // Delete a task
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted successfully");
    }
//
//    // Assign a task to a user
//    @PostMapping("/{taskId}/assign/{userId}")
//    public ResponseEntity<String> assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId) {
//        taskService.assignTaskToUser(taskId, userId);
//        return ResponseEntity.ok("Task assigned successfully");
//    }
//
//    // Update task status
//    @PatchMapping("/{taskId}/status")
//    public ResponseEntity<Task> updateTaskStatus(@PathVariable Long taskId, @RequestParam String status) {
//        Task updatedTask = taskService.updateTaskStatus(taskId, status);
//        return ResponseEntity.ok(updatedTask);
//    }
}
