package com.tms.controller;


import com.tms.dto.ApiResponse;
import com.tms.dto.TaskCategoryDTO;
import com.tms.model.Category;
import com.tms.service.TaskCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taskcategories")
public class TaskCategoryController {
	
	 @Autowired
	    private TaskCategoryService taskCategoryService;

	    // Endpoint to associate tasks with categories
	    @PostMapping("/post")
	    public ResponseEntity<String> associateTaskWithCategories(@Valid @RequestBody List<TaskCategoryDTO> taskCategoryDTOs) {
	       
	            boolean isSuccessful = taskCategoryService.associateTaskWithCategories(taskCategoryDTOs);
	            if (isSuccessful) {
	                return new ResponseEntity<>("Task-category added successfully.", HttpStatus.CREATED);
	            } else {
	                return new ResponseEntity<>("Task-Category already exists", HttpStatus.CONFLICT);
	            }

//	            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    // Endpoint to get categories for a specific task
	    @GetMapping("/{taskId}")
	    public ResponseEntity<?> getCategoriesForTask(@PathVariable Integer taskId) {
	        List<Category> categories = taskCategoryService.getCategoriesForTask(taskId);

	        if (categories == null || categories.isEmpty()) {
	            return new ResponseEntity<>(new ApiResponse("GETFAILS", "No category found for this task"), HttpStatus.NOT_FOUND);
	        }

	        return new ResponseEntity<>(categories, HttpStatus.OK);
	    }

	    // Endpoint to get tasks for a specific category
	    @GetMapping("/category/{categoryId}")
	    public ResponseEntity<?> getTasksForCategory(@PathVariable Integer categoryId) {
	        List<TaskCategoryDTO> taskCategories = taskCategoryService.getTasksForCategory(categoryId);

	        if (taskCategories == null || taskCategories.isEmpty()) {
	            return new ResponseEntity<>(new ApiResponse("GETFAILS", "No tasks found for category with ID: " + categoryId), HttpStatus.NOT_FOUND);
	        }

	        return new ResponseEntity<>(taskCategories, HttpStatus.OK);
	    }

}
