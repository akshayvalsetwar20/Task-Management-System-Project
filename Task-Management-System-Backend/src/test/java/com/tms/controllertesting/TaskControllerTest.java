package com.tms.controllertesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.tms.controller.TaskController;
import com.tms.dto.ApiResponse;
import com.tms.dto.TaskDTO;
import com.tms.service.TaskService;

public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTask() {
        TaskDTO taskDTO = new TaskDTO();
        // Initialize taskDTO properties as needed

        doNothing().when(taskService).createTask(any(TaskDTO.class)); // Mock the service call

        ResponseEntity<ApiResponse> response = taskController.createTask(taskDTO);

        // Verify response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("POSTSUCCESS", response.getBody().getCode()); // Ensure this matches your ApiResponse structure
        assertEquals("Task added successfully", response.getBody().getMessage());

        verify(taskService, times(1)).createTask(taskDTO); // Verify service method was called once
    }

    @Test
    public void testUpdateTask() {
        Integer taskId = 1;
        TaskDTO updatedTaskDTO = new TaskDTO();
        // Initialize updatedTaskDTO properties as needed

        doNothing().when(taskService).updateTask(anyInt(), any(TaskDTO.class)); // Mock the service call

        ResponseEntity<ApiResponse> response = taskController.updateTask(taskId, updatedTaskDTO);

        // Verify response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("UPDATESUCCESS", response.getBody().getCode()); // Ensure this matches your ApiResponse structure
        assertEquals("Task updated successfully", response.getBody().getMessage());

        verify(taskService, times(1)).updateTask(taskId, updatedTaskDTO); // Verify service method was called once
    }

    @Test
    public void testDeleteTask() {
        Integer taskId = 1;

        doNothing().when(taskService).deleteTask(anyInt()); // Mock the service call

        ResponseEntity<ApiResponse> response = taskController.deleteTask(taskId);

        // Verify response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("DELETESUCCESS", response.getBody().getCode()); // Ensure this matches your ApiResponse structure
        assertEquals("Task deleted successfully", response.getBody().getMessage());

        verify(taskService, times(1)).deleteTask(taskId); // Verify service method was called once
    }

    @Test
    public void testGetOverdueTasks() {
        List<TaskDTO> overdueTasks = Arrays.asList(new TaskDTO(), new TaskDTO());
        when(taskService.getOverdueTasks()).thenReturn(overdueTasks); // Mock the service call

        ResponseEntity<List<TaskDTO>> response = taskController.getOverdueTasks();

        // Verify response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(overdueTasks, response.getBody());

        verify(taskService, times(1)).getOverdueTasks(); // Verify service method was called once
    }

    @Test
    public void testGetTasksByPriorityAndStatus() {
        String priority = "high";
        String status = "open";
        List<TaskDTO> tasks = Arrays.asList(new TaskDTO(), new TaskDTO());
        
        when(taskService.getTasksByPriorityAndStatus(priority, status)).thenReturn(tasks); // Mock the service call

        ResponseEntity<List<TaskDTO>> response = taskController.getTasksByPriorityAndStatus(priority, status);

        // Verify response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(tasks, response.getBody());

        verify(taskService, times(1)).getTasksByPriorityAndStatus(priority, status); // Verify service method was called once
    }

    @Test
    public void testGetTasksDueSoon() {
        List<TaskDTO> dueSoonTasks = Arrays.asList(new TaskDTO(), new TaskDTO());
        
        when(taskService.getTasksDueSoon()).thenReturn(dueSoonTasks); // Mock the service call

        ResponseEntity<List<TaskDTO>> response = taskController.getTasksDueSoon();

        // Verify response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dueSoonTasks, response.getBody());

        verify(taskService, times(1)).getTasksDueSoon(); // Verify service method was called once
    }

    @Test
    public void testGetTasksByUserAndStatus() {
        Integer userId = 1;
        String status = "completed";
        
        List<TaskDTO> tasks = Arrays.asList(new TaskDTO(), new TaskDTO());
        
        when(taskService.getTasksByUserAndStatus(userId, status)).thenReturn(tasks); // Mock the service call

        ResponseEntity<List<TaskDTO>> response = taskController.getTasksByUserAndStatus(userId, status);

        // Verify response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(tasks, response.getBody());

        verify(taskService, times(1)).getTasksByUserAndStatus(userId, status); // Verify service method was called once
    }

    @Test
    public void testGetTasksByCategory() {
        Integer categoryId = 1;
        
        List<TaskDTO> tasks = Arrays.asList(new TaskDTO(), new TaskDTO());
        
        when(taskService.getTasksByCategoryId(categoryId)).thenReturn(tasks); // Mock the service call

        ResponseEntity<List<TaskDTO>> response = taskController.getTasksByCategory(categoryId);

        // Verify response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(tasks, response.getBody());

        verify(taskService, times(1)).getTasksByCategoryId(categoryId); // Verify service method was called once
    }
}
