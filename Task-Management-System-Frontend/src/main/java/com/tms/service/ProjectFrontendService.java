package com.tms.service;


import com.tms.dto.ApiResponse;
import com.tms.dto.ProjectFDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProjectFrontendService {

    private static final String BASE_URL = "http://localhost:9091/api/projects";

    @Autowired
    private RestTemplate restTemplate;
  
    // Fetch all projects
    public List<ProjectFDTO> getOngoingProjects() {
        ProjectFDTO[] projects = restTemplate.getForObject(BASE_URL + "/ongoing", ProjectFDTO[].class);
        return Arrays.asList(projects);
    }
    // Create a new project
    public void createProject(ProjectFDTO projectDTO) {
        restTemplate.postForObject(BASE_URL + "/post", projectDTO, ProjectFDTO.class);
    }
    
 // Fetch projects within a specified date range
    public List<ProjectFDTO> getProjectsByDateRange(String startDate, String endDate) {
        String url = BASE_URL + "/date-range/" + startDate + "/" + endDate;
        ProjectFDTO[] projects = restTemplate.getForObject(url, ProjectFDTO[].class);
        return List.of(projects);
    }
    
//    Fetch projects by id 
    public ProjectFDTO getProjectsByProjectID(Long projectId) {
        String url = BASE_URL+"/"+projectId;
        ProjectFDTO projects = restTemplate.getForObject(url, ProjectFDTO.class);
        return projects;
    }
    
//    // Fetch projects by status
//    public List<ProjectFDTO> getProjectsByStatus(String status) {
//        String url = BASE_URL + "/" + status;
//        ProjectFDTO[] projects = restTemplate.getForObject(url, ProjectFDTO[].class);
//        return List.of(projects); // Convert array to list and return
//    }
//    
    
 // Fetch projects by status
    public void updateProject(Long projectId,ProjectFDTO projectFDTO) {
        String url = BASE_URL + "/" + "update/"+projectId;
        restTemplate.put(url, projectFDTO,ProjectFDTO.class);
    }
    
    // Method to update the project details
    public ApiResponse updateProject(ProjectFDTO updatedProjectDTO) {
        String url = BASE_URL + "/update/" + updatedProjectDTO.getProjectID();
        
        // Assuming you're using RestTemplate to update the project via an API
        restTemplate.put(url, updatedProjectDTO);

        return new ApiResponse("UPDATESUCCESS", "Project updated successfully");
    }
//  Fetch projects by id 
  public ProjectFDTO getProjectsByProjectID1(Integer projectId) {
      String url = BASE_URL+"/"+projectId;
      ProjectFDTO projects = restTemplate.getForObject(url, ProjectFDTO.class);
      return projects;
  }
  // Method to delete project by ID (soft delete)
  public String deleteProject(Integer projectId) {
      String url = BASE_URL + "/delete/" + projectId;  // Construct URL for delete operation

      try {
          // Send DELETE request to delete the project
          restTemplate.delete(url);  // This sends a DELETE request to the given URL
          return "Project deleted successfully.";  // Return success message
      } catch (Exception e) {
          // Handle any exceptions and return a failure message
          return "Failed to delete project: " + e.getMessage();
      }
  }
  public List<ProjectFDTO> getHighPriorityProjects() {
	    // Assuming the BASE_URL points to the backend API endpoint for high-priority projects
	    String url = BASE_URL + "/high-priority-tasks";
	    
	    // Ensure that we're getting a List of ProjectFDTO objects, not just a single object.
	    // You can use ParameterizedTypeReference to specify the expected response type.
	    ResponseEntity<List<ProjectFDTO>> response = restTemplate.exchange(
	        url,
	        HttpMethod.GET,
	        null, // No request body, as this is a GET request
	        new ParameterizedTypeReference<List<ProjectFDTO>>() {}
	    );
	    
	    // Return the list of projects fetched from the API
	    return response.getBody();
	}

}
