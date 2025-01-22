package com.tms.controllertesting;

import com.tms.controller.TaskCategoryController;
import com.tms.dto.ApiResponse;
import com.tms.dto.TaskCategoryDTO;
import com.tms.model.Category;
import com.tms.model.Task;
import com.tms.service.TaskCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class TaskCategoryControllerTest {

    @InjectMocks
    private TaskCategoryController taskCategoryController;

    @Mock
    private TaskCategoryService taskCategoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssociateTaskWithCategories_Success() {
        List<TaskCategoryDTO> taskCategoryDTOs = new ArrayList<>();
        taskCategoryDTOs.add(new TaskCategoryDTO(1, 1, new Task(), new Category()));

        when(taskCategoryService.associateTaskWithCategories(any())).thenReturn(true);

        ResponseEntity<String> response = taskCategoryController.associateTaskWithCategories(taskCategoryDTOs);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Task-category added successfully.", response.getBody());
    }

    @Test
    public void testAssociateTaskWithCategories_Conflict() {
        List<TaskCategoryDTO> taskCategoryDTOs = new ArrayList<>();
        taskCategoryDTOs.add(new TaskCategoryDTO(1, 1, new Task(), new Category()));

        when(taskCategoryService.associateTaskWithCategories(any())).thenReturn(false);

        ResponseEntity<String> response = taskCategoryController.associateTaskWithCategories(taskCategoryDTOs);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Task-Category already exists", response.getBody());
    }

    @Test
    public void testAssociateTaskWithCategories_Exception() {
        List<TaskCategoryDTO> taskCategoryDTOs = new ArrayList<>();
        taskCategoryDTOs.add(new TaskCategoryDTO(1, 1, new Task(), new Category()));

        when(taskCategoryService.associateTaskWithCategories(any())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<String> response = taskCategoryController.associateTaskWithCategories(taskCategoryDTOs);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: Error", response.getBody());
    }

    @Test
    public void testGetCategoriesForTask_Success() {
        List<Category> categories = Arrays.asList(new Category(), new Category());
        
        when(taskCategoryService.getCategoriesForTask(anyInt())).thenReturn(categories);

        ResponseEntity<?> response = taskCategoryController.getCategoriesForTask(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categories, response.getBody());
    }

//    @Test
//    public void testGetCategoriesForTask_NotFound() {
//        when(taskCategoryService.getCategoriesForTask(anyInt())).thenReturn(new ArrayList<>());
//
//        ResponseEntity<?> response = taskCategoryController.getCategoriesForTask(1);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals(new ApiResponse("GETFAILS", "No category found for this task"), response.getBody());
//    }

    @Test
    public void testGetTasksForCategory_Success() {
        List<TaskCategoryDTO> taskCategories = Arrays.asList(
                new TaskCategoryDTO(1, 1, new Task(), new Category()),
                new TaskCategoryDTO(2, 1, new Task(), new Category())
        );

        when(taskCategoryService.getTasksForCategory(anyInt())).thenReturn(taskCategories);

        ResponseEntity<?> response = taskCategoryController.getTasksForCategory(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskCategories, response.getBody());
    }

//    @Test
//    public void testGetTasksForCategory_NotFound() {
//        when(taskCategoryService.getTasksForCategory(anyInt())).thenThrow(new RuntimeException("No tasks found for this category"));
//
//        ResponseEntity<?> response = taskCategoryController.getTasksForCategory(1);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals(new ApiResponse("GETFAILS", "No tasks found for category with ID: 1"), response.getBody());
//    }
}
