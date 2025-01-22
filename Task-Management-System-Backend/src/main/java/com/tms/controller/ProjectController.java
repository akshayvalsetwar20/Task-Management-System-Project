package com.tms.controller;
import com.tms.dto.ApiResponse;
import com.tms.dto.ProjectDTO;
import com.tms.service.ProjectService;
import com.tms.exception.HighPriorityTaskEmptyException;
import com.tms.exception.ProjectAlreadyExistsException;
import com.tms.exception.ProjectListEmptyException;
import com.tms.exception.ProjectNotFoundException;
import com.tms.model.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
	
	@Autowired
    private ProjectService projectService;

 // POST: Create a new project
    @PostMapping("/post")
    public ResponseEntity<?> createProject(@RequestBody ProjectDTO projectDTO) {
        try {
            // Try to create the project
            ProjectDTO createdProjectDTO = projectService.createProject(projectDTO);

            // Return the created project with status 201 (Created)
            return new ResponseEntity<>(new ApiResponse("POSTSUCCESS", "Project added successfully"), HttpStatus.CREATED);

        } catch (ProjectAlreadyExistsException e) {
            // If project already exists, return a bad request with appropriate message
            ApiResponse response = new ApiResponse("ADDFAILS", "Project already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            
        } catch (IllegalArgumentException e) {
            // Handle invalid argument errors
            ApiResponse response = new ApiResponse("INVALIDARGS", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            ApiResponse response = new ApiResponse("ERROR", "An unexpected error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    // GET: Fetch all ongoing projects
    @GetMapping("/ongoing")
    public ResponseEntity<?> getOngoingProjects() {
        try {
            List<ProjectDTO> ongoingProjects = projectService.getOngoingProjects();
            return new ResponseEntity<>(ongoingProjects, HttpStatus.OK);
        } catch (ProjectListEmptyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // GET: Fetch projects by date range
    @GetMapping("/date-range/{startDate}/{endDate}")
    public ResponseEntity<?> getProjectsByDateRange(@PathVariable("startDate") String startDateStr,
            @PathVariable("endDate") String endDateStr) {
        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            List<ProjectDTO> projects = projectService.getProjectsByDateRange(startDate, endDate);
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

 // PUT: Update an existing project
    @PutMapping("/update/{projectId}")
    public ResponseEntity<?> updateProject(@PathVariable("projectId") Integer projectId, 
                                           @RequestBody ProjectDTO updatedProjectDTO) {
        try {
            // Attempt to update the project
            ProjectDTO updatedProject = projectService.updateProject(projectId, updatedProjectDTO);
            
            // If the update is successful, return a success message with a 200 OK status
            ApiResponse response = new ApiResponse("UPDATESUCCESS", "Project updated successfully");
            return ResponseEntity.ok(response);
            
        } catch (ProjectNotFoundException e) {
            // If the project doesn't exist, return a 404 Not Found status with a message
            ApiResponse response = new ApiResponse("UPDTFAILS", "Project doesn't exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            // Handle any other exceptions
            ApiResponse response = new ApiResponse("UPDTFAILS", "An error occurred while updating the project");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    // DELETE: Soft delete a project
    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Integer projectId) {
        try {
            projectService.deleteProject(projectId);
            return ResponseEntity.ok("Project deleted successfully.");
        } catch (ProjectNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // GET: Fetch projects by status
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getProjectsByStatus(@PathVariable String status) {
        try {
            List<ProjectDTO> projects = projectService.getProjectsByStatus(status);
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (ProjectListEmptyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @GetMapping("/high-priority-tasks")
    public ResponseEntity<Object> getHighPriorityProjects() {
        try {
            List<ProjectDTO> highPriorityProjects = projectService.getHighPriorityProjects();
            return ResponseEntity.ok(highPriorityProjects); // Success response

        } catch (HighPriorityTaskEmptyException ex) {
            // The exception will be caught by the global exception handler
            throw ex;
        } catch (Exception ex) {
            // Handle unexpected errors
            throw new RuntimeException("An unexpected error occurred while fetching high-priority projects", ex);
        }
    }
    
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable("projectId") Integer projectId) {
        try {
            // Get the project by ID from the service
            ProjectDTO projectDTO = projectService.getProjectById(projectId);
 
            // Return the project with a 200 OK status
            return new ResponseEntity<>(projectDTO, HttpStatus.OK);
 
        } catch (ProjectNotFoundException e) {
            // If project not found, return a 404 Not Found status with a message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Handle any unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred while fetching the project.");
        }
    }
    
    @GetMapping("/user-role/{roleName}")
    public ResponseEntity<?> getProjectsByRole(@PathVariable String roleName) {
//        try {
            List<ProjectDTO> projects = projectService.getProjectsByRoleName(roleName);
            return ResponseEntity.ok(projects);
    }
}
