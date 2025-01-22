package com.tms.service;

import com.tms.dto.TaskDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class TaskFrontendService {

	private final RestTemplate restTemplate = new RestTemplate();
	private static final String BASE_URL = "http://localhost:9091/api/tasks";

	// Create a task
	public void createTask(TaskDTO taskDTO) {
		String url = BASE_URL + "/post";
		restTemplate.postForEntity(url, taskDTO, Void.class);
	}

	// Get overdue tasks
	public List<TaskDTO> getOverdueTasks() {
		String url = BASE_URL + "/overdue";
		TaskDTO[] tasks = restTemplate.getForObject(url, TaskDTO[].class);
		return List.of(tasks);
	}

	// Get tasks by priority and status
	public List<TaskDTO> getTasksByPriorityAndStatus(String priority, String status) {
		try {
			String url = BASE_URL + "/priority/" + priority + "/status/" + status;
			ResponseEntity<TaskDTO[]> response = restTemplate.getForEntity(url, TaskDTO[].class);
			if (response.getStatusCode() == HttpStatus.OK) {
				return Arrays.asList(response.getBody()); // Convert array to list
			} else {
				return List.of(); // Return an empty list if the status is not OK
			}
		} catch (RestClientException e) {
			// Log the error
			System.err.println("Error fetching tasks: " + e.getMessage());
			return List.of(); // Return an empty list on failure
		}
	}

	// Get tasks due soon
	public List<TaskDTO> getTasksDueSoon() {
		String url = BASE_URL + "/due-soon";
		TaskDTO[] tasks = restTemplate.getForObject(url, TaskDTO[].class);
		return List.of(tasks);
	}

	public void softDeleteTask(Integer taskId) {
		String url = BASE_URL + "/" + taskId; // Change this to a soft-delete endpoint
		restTemplate.delete(url); // Sending a PUT request to mark the task as deleted
	}

	public void updateTask(Integer taskId, TaskDTO updatedTaskDTO) {
		String url = BASE_URL + "/update/" + taskId;
		restTemplate.put(BASE_URL, updatedTaskDTO, taskId);

	}

//    // Get tasks by user and status
//    public List<TaskDTO> getTasksByUserAndStatus(Integer userId, String status) {
//        String url = backendApiUrl + "/user/" + userId + "/status/" + status;
//        TaskDTO[] tasks = restTemplate.getForObject(url, TaskDTO[].class);
//        return List.of(tasks);
//    }

//    // Get tasks by category
//    public List<TaskDTO> getTasksByCategoryId(Integer categoryId) {
//        String url = backendApiUrl + "/category/" + categoryId;
//        TaskDTO[] tasks = restTemplate.getForObject(url, TaskDTO[].class);
//        return List.of(tasks);
//    }

	// Get task by ID
	public TaskDTO getTaskById(Integer taskId) {
		String url = BASE_URL + "/" + taskId;
		return restTemplate.getForObject(url, TaskDTO.class);
	}
//
    // Update a task
//    public void updateTask(Integer taskId, TaskDTO updatedTaskDTO) {
//        String url = backendApiUrl + "/update/" + taskId;
//        restTemplate.put(url, updatedTaskDTO);
//    }

//    public TaskDTO getTaskById(Integer taskId) {
//        // Fetch task details from the backend
//        String url = backendApiUrl + "/" + taskId;
//        return restTemplate.getForObject(url, TaskDTO.class);
//    }

//    // Delete a task
//    public void deleteTask(Integer taskId) {
//        String url = backendApiUrl + "/" + taskId;
//        restTemplate.delete(url);
//    }

}
