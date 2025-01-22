package com.tms.service;

import com.tms.dto.CategoryDTO;
import com.tms.dto.ProjectFDTO;
import com.tms.dto.TaskCategoryDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class TaskCategoryFrontendService {
	 @Autowired
	    private RestTemplate restTemplate;
	
	 private static final String BASE_URL = "http://localhost:9091/api/taskcategories";
	 
	 public boolean associateTaskWithCategories(List<TaskCategoryDTO> taskCategoryDTOs) {
	        try {
	            String url = BASE_URL + "/post";
	            restTemplate.postForObject(url, taskCategoryDTOs, String.class);
	            return true;
	        } catch (Exception e) {
	            return false;
	        }
	    }
	 // Method to get categories for a specific task
	    public List<CategoryDTO> getCategoriesForTask(Integer taskId) {
	        try {
	            // URL for fetching categories by task ID
	            String url = BASE_URL + "/" + taskId; 

	            // Send a GET request and receive a List of Category objects
	            List<CategoryDTO> categories = restTemplate.getForObject(url, List.class);

	            return categories;  // Return the list of categories for the given task
	        } catch (Exception e) {
	            // Log or handle the error (if required)
	            return null;  // Return null if there was an issue fetching the categories
	        }
	    }
	    // Fetch tasks for a specific category
	    public List<TaskCategoryDTO> getTasksForCategory(Integer categoryId) {
	        try {
	            String url = BASE_URL + "/category/" + categoryId;  // Assuming the backend URL for fetching tasks by category ID
	            List<TaskCategoryDTO> taskCategories = restTemplate.getForObject(url, List.class);
	            return taskCategories;
	        } catch (Exception e) {
	            return null;
	        }
	    }

}
