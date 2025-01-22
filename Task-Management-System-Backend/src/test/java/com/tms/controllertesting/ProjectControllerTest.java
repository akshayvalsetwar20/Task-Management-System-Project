package com.tms.controllertesting;

import com.tms.controller.ProjectController;
import com.tms.dto.ApiResponse;
import com.tms.dto.ProjectDTO;
import com.tms.exception.HighPriorityTaskEmptyException;
import com.tms.exception.ProjectAlreadyExistsException;
import com.tms.exception.ProjectListEmptyException;
import com.tms.exception.ProjectNotFoundException;
import com.tms.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class ProjectControllerTest {

    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectService projectService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProject_Success() throws ProjectAlreadyExistsException {
        ProjectDTO projectDTO = new ProjectDTO(null, "Project 1", "Description", LocalDate.now(), LocalDate.now().plusDays(10), 1);

        when(projectService.createProject(any(ProjectDTO.class))).thenReturn(projectDTO);

        ResponseEntity<?> response = projectController.createProject(projectDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ApiResponse apiResponse = (ApiResponse) response.getBody();
        assertEquals("POSTSUCCESS", apiResponse.getCode());
        assertEquals("Project added successfully", apiResponse.getMessage());
    }

    @Test
    public void testCreateProject_AlreadyExists() throws ProjectAlreadyExistsException {
        ProjectDTO projectDTO = new ProjectDTO(null, "Project 1", "Description", LocalDate.now(), LocalDate.now().plusDays(10), 1);

        when(projectService.createProject(any(ProjectDTO.class))).thenThrow(new ProjectAlreadyExistsException("Project already exists"));

        ResponseEntity<?> response = projectController.createProject(projectDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ApiResponse apiResponse = (ApiResponse) response.getBody();
        assertEquals("ADDFAILS", apiResponse.getCode());
        assertEquals("Project already exists", apiResponse.getMessage());
    }

    @Test
    public void testGetOngoingProjects_Success() throws ProjectListEmptyException {
        List<ProjectDTO> ongoingProjects = Arrays.asList(new ProjectDTO(1, "Ongoing Project", "Description", LocalDate.now().minusDays(1), LocalDate.now().plusDays(5), 1));

        when(projectService.getOngoingProjects()).thenReturn(ongoingProjects);

        ResponseEntity<?> response = projectController.getOngoingProjects();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ongoingProjects, response.getBody());
    }

    @Test
    public void testGetOngoingProjects_NotFound() throws ProjectListEmptyException {
        when(projectService.getOngoingProjects()).thenThrow(new ProjectListEmptyException("Project list is empty"));

        ResponseEntity<?> response = projectController.getOngoingProjects();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Project list is empty", response.getBody());
    }

    @Test
    public void testGetProjectsByDateRange_Success() throws ProjectListEmptyException {
        List<ProjectDTO> projects = Arrays.asList(new ProjectDTO(1, "Project 1", "Description", LocalDate.now(), LocalDate.now().plusDays(5), 1));

        when(projectService.getProjectsByDateRange(any(LocalDate.class), any(LocalDate.class))).thenReturn(projects);

        ResponseEntity<?> response = projectController.getProjectsByDateRange("2023-01-01", "2023-12-31");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

//   @Test
//   public void testGetProjectsByDateRange_BadRequest() throws ProjectListEmptyException {
//       when(projectService.getProjectsByDateRange(any(LocalDate.class), any(LocalDate.class))).thenThrow(new RuntimeException("Invalid date range"));
//
//       ResponseEntity<?> response = projectController.getProjectsByDateRange("invalid-date", "2023-12-31");
//
//       assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//       assertEquals("Invalid date range", response.getBody());
//   }

   @Test
   public void testUpdateProject_Success() {
       ProjectDTO updatedProjectDTO = new ProjectDTO(1, "Updated Project", "Updated Description", LocalDate.now(), LocalDate.now().plusDays(10), 1);
       
       when(projectService.updateProject(anyInt(), any(ProjectDTO.class))).thenReturn(updatedProjectDTO);

       ResponseEntity<?> response = projectController.updateProject(1, updatedProjectDTO);

       assertEquals(HttpStatus.OK, response.getStatusCode());
       ApiResponse apiResponse = (ApiResponse) response.getBody();
       assertEquals("UPDATESUCCESS", apiResponse.getCode());
       assertEquals("Project updated successfully", apiResponse.getMessage());
   }

   @Test
   public void testUpdateProject_NotFound() {
       ProjectDTO updatedProjectDTO = new ProjectDTO(1, "Updated Project", "Updated Description", LocalDate.now(), LocalDate.now().plusDays(10), 1);

       when(projectService.updateProject(anyInt(), any(ProjectDTO.class))).thenThrow(new ProjectNotFoundException("Project doesn't exist"));

       ResponseEntity<?> response = projectController.updateProject(1, updatedProjectDTO);

       assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
       ApiResponse apiResponse = (ApiResponse) response.getBody();
       assertEquals("UPDTFAILS", apiResponse.getCode());
       assertEquals("Project doesn't exist", apiResponse.getMessage());
   }

   @Test
   public void testDeleteProject_Success() {
       ResponseEntity<?> response = projectController.deleteProject(1);
       
       assertEquals(HttpStatus.OK, response.getStatusCode());
       assertEquals("Project deleted successfully.", response.getBody());
   }

   @Test
   public void testDeleteProject_NotFound() {
       doThrow(new ProjectNotFoundException("Project doesn't exist")).when(projectService).deleteProject(anyInt());

       ResponseEntity<?> response = projectController.deleteProject(1);
       
       assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
       assertEquals("Project doesn't exist", response.getBody());
   }

   @Test
   public void testGetProjectsByStatus_Success() throws ProjectListEmptyException {
       List<ProjectDTO> projects = Arrays.asList(new ProjectDTO(1, "Completed Project", "Description", LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), 1));

       when(projectService.getProjectsByStatus(any(String.class))).thenReturn(projects);

       ResponseEntity<?> response = projectController.getProjectsByStatus("Completed");

       assertEquals(HttpStatus.OK, response.getStatusCode());
       assertEquals(projects, response.getBody());
   }

   @Test
   public void testGetProjectsByStatus_NotFound() throws ProjectListEmptyException {
       when(projectService.getProjectsByStatus(any(String.class))).thenThrow(new ProjectListEmptyException("No projects found with status Completed"));

       ResponseEntity<?> response = projectController.getProjectsByStatus("Completed");

       assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
       assertEquals("No projects found with status Completed", response.getBody());
   }

   @Test
   public void testGetHighPriorityProjects_Success() {
       List<ProjectDTO> highPriorityProjects = Arrays.asList(new ProjectDTO(1, "High Priority Project", "Description", LocalDate.now(), LocalDate.now().plusDays(5), 1));

       when(projectService.getHighPriorityProjects()).thenReturn(highPriorityProjects);

       ResponseEntity<Object> response = projectController.getHighPriorityProjects();

       assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
       assertEquals(highPriorityProjects, response.getBody());
   }

   @Test
   public void testGetHighPriorityProjects_EmptyException() {
         when(projectService.getHighPriorityProjects()).thenThrow(new HighPriorityTaskEmptyException("No high-priority tasks found."));

         try {
             projectController.getHighPriorityProjects();
         } catch (HighPriorityTaskEmptyException ex) {
             // Assert that the exception message is correct
             assertEquals("No high-priority tasks found.", ex.getMessage());
         }
     }
}
