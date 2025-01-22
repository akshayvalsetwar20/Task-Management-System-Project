package com.tms.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import com.tms.dto.ApiResponse;
import com.tms.dto.TaskDTO;
import com.tms.service.TaskService;
 
import jakarta.validation.Valid;
import java.util.List;
 
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
 
    @Autowired
    TaskService taskService;
 
    // Create a new task using TaskDTO
    @PostMapping("/post")
    public ResponseEntity<ApiResponse> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        taskService.createTask(taskDTO);
        return ResponseEntity.ok(new ApiResponse("POSTSUCCESS", "Task added successfully"));
    }
 
    // Get tasks that are overdue
    @GetMapping("/overdue")
    public ResponseEntity<List<TaskDTO>> getOverdueTasks() {
        return ResponseEntity.ok(taskService.getOverdueTasks());
    }
    
 // Get tasks by id 
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Integer taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }
 
    // Get tasks by priority and status
    @GetMapping("/priority/{priority}/status/{status}")
    public ResponseEntity<List<TaskDTO>> getTasksByPriorityAndStatus(@PathVariable String priority, @PathVariable String status) {
        return ResponseEntity.ok(taskService.getTasksByPriorityAndStatus(priority, status));
    }
 
    // Get tasks that are due soon (within 7 days)
    @GetMapping("/due-soon")
    public ResponseEntity<List<TaskDTO>> getTasksDueSoon() {
        return ResponseEntity.ok(taskService.getTasksDueSoon());
    }
 
    // Get tasks by user and status
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<TaskDTO>> getTasksByUserAndStatus(@PathVariable Integer userId, @PathVariable String status) {
        return ResponseEntity.ok(taskService.getTasksByUserAndStatus(userId, status));
    }
 
    // Get tasks by category ID
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<TaskDTO>> getTasksByCategory(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(taskService.getTasksByCategoryId(categoryId));
    }
 
    // Update an existing task using TaskDTO
    @PutMapping("/update/{taskId}")
    public ResponseEntity<ApiResponse> updateTask(@PathVariable Integer taskId, @Valid @RequestBody TaskDTO updatedTaskDTO) {
        taskService.updateTask(taskId, updatedTaskDTO);
        return ResponseEntity.ok(new ApiResponse("UPDATESUCCESS", "Task updated successfully"));
    }
 
    // Delete a task (soft delete)
    @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable Integer taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok(new ApiResponse("DELETESUCCESS", "Task deleted successfully"));
    }
}