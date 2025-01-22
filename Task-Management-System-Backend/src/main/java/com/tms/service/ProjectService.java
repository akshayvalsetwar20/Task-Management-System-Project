package com.tms.service;

import com.tms.dto.ProjectDTO;
import com.tms.model.Project;
import com.tms.model.Task;
import com.tms.model.User;
import com.tms.repository.ProjectRepository;
import com.tms.repository.TaskRepository;
import com.tms.repository.UserRepository;
import com.tms.exception.ProjectAlreadyExistsException;
import com.tms.exception.ProjectListEmptyException;
import com.tms.exception.ProjectNotFoundException;
import com.tms.exception.HighPriorityTaskEmptyException;
import com.tms.exception.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService implements ProjectServiceImpl{
	
	 @Autowired
	    private ProjectRepository projectRepository;

	    @Autowired
	    private UserRepository userRepository;
	    
	    @Autowired
	    private TaskRepository taskRepository;

	    // Method to create a new project from ProjectDTO
	    public ProjectDTO createProject(ProjectDTO projectDTO) throws ProjectAlreadyExistsException {
	        // Check if the project already exists by name
	        Optional<Project> existingProject = projectRepository.findByProjectName(projectDTO.getProjectName());
	        if (existingProject.isPresent()) {
	            throw new ProjectAlreadyExistsException("Project with the name '" + projectDTO.getProjectName() + "' already exists.");
	        }

	        // Ensure the user exists
	        User user = userRepository.findById(projectDTO.getUserId())
	                                  .orElseThrow(() -> new UserNotFoundException("User not found"));

	        // Create Project entity
	        Project project = new Project(projectDTO.getProjectName(), projectDTO.getDescription(),
	                                      projectDTO.getStartDate(), projectDTO.getEndDate(), user);

	        // Save the project and convert it to DTO before returning
	        project = projectRepository.save(project);
	        return new ProjectDTO(project.getProjectID(), project.getProjectName(), project.getDescription(),
	                              project.getStartDate(), project.getEndDate(), user.getUserID());
	    }

	    // Method to get all ongoing projects
	    public List<ProjectDTO> getOngoingProjects() throws ProjectListEmptyException {
	        LocalDate today = LocalDate.now();
	        // Get the projects where the start date is before today and the end date is after today
	        List<Project> ongoingProjects = projectRepository.findByStartDateBeforeAndEndDateAfter(today, today);

	        // Check if there are no ongoing projects
	        if (ongoingProjects.isEmpty()) {
	            throw new ProjectListEmptyException("Project list is empty");
	        }

	        // Convert to DTO and return
	        return ongoingProjects.stream()
	                              .map(project -> new ProjectDTO(project.getProjectID(), project.getProjectName(),
	                                                             project.getDescription(), project.getStartDate(),
	                                                             project.getEndDate(), project.getUser().getUserID()))
	                              .collect(Collectors.toList());
	    }

	    // Method to get projects within a specified date range
	    public List<ProjectDTO> getProjectsByDateRange(LocalDate startDate, LocalDate endDate) throws ProjectListEmptyException {
	        List<Project> projects = projectRepository.findByStartDateBetween(startDate, endDate);

	        // Throw exception if no projects found
	        if (projects.isEmpty()) {
	            throw new ProjectListEmptyException("No projects found within the given date range.");
	        }

	        return projects.stream()
	                       .map(project -> new ProjectDTO(project.getProjectID(), project.getProjectName(),
	                                                      project.getDescription(), project.getStartDate(),
	                                                      project.getEndDate(), project.getUser().getUserID()))
	                       .collect(Collectors.toList());
	    }

	    // Method to perform soft delete
	    public void deleteProject(Integer projectId) throws ProjectNotFoundException {
	        // Find the project by ID
	        Optional<Project> projectOpt = projectRepository.findByProjectID(projectId);

	        if (projectOpt.isEmpty()) {
	            throw new ProjectNotFoundException("Project doesn't exist");
	        }

	        Project project = projectOpt.get();
	        // Soft delete by marking the project as deleted (no need to actually delete it from the database)
	        project.setDeleted(true);
	        projectRepository.save(project);
	    }

	    // Method to update an existing project
	    public ProjectDTO updateProject(Integer projectId, ProjectDTO updatedProjectDTO) throws ProjectNotFoundException {
	        // Check if the project exists
	        Project existingProject = projectRepository.findByProjectID(projectId)
	                                                   .orElseThrow(() ->new ProjectNotFoundException("Project doesn't exist"));

	        // Update project details
	        existingProject.setProjectName(updatedProjectDTO.getProjectName());
	        existingProject.setDescription(updatedProjectDTO.getDescription());
	        existingProject.setStartDate(updatedProjectDTO.getStartDate());
	        existingProject.setEndDate(updatedProjectDTO.getEndDate());

	        // Save the updated project and convert to DTO
	        existingProject = projectRepository.save(existingProject);

	        return new ProjectDTO(existingProject.getProjectID(), existingProject.getProjectName(),
	                              existingProject.getDescription(), existingProject.getStartDate(),
	                              existingProject.getEndDate(), existingProject.getUser().getUserID());
	    }

	    // Method to get projects by status (assumes you have a status field in Project)
	    public List<ProjectDTO> getProjectsByStatus(String status) throws ProjectListEmptyException {
	        List<Project> projects = projectRepository.findProjectsByTaskStatus(status);

	        if (projects.isEmpty()) {
	            throw new ProjectListEmptyException("No projects found with status " + status);
	        }

	        return projects.stream()
	                       .map(project -> new ProjectDTO(project.getProjectID(), project.getProjectName(),
	                                                      project.getDescription(), project.getStartDate(),
	                                                      project.getEndDate(), project.getUser().getUserID()))
	                       .collect(Collectors.toList());
	    }

	    // Method to get high-priority projects based on tasks
	    public List<ProjectDTO> getHighPriorityProjects() {
	        List<Task> highPriorityTasks = taskRepository.findByPriority("High"); // Assuming 'priority' field is available in Task entity
	        if (highPriorityTasks.isEmpty()) {
	            throw new HighPriorityTaskEmptyException("No high-priority tasks found.");
	        }

	        return highPriorityTasks.stream()
	            .map(task -> {
	                Project project = task.getProject();
	                return new ProjectDTO(
	                    project.getProjectID(),
	                    project.getProjectName(),
	                    project.getDescription(),
	                    project.getStartDate(),
	                    project.getEndDate(),
	                    project.getUser().getUserID()
	                );
	            })
	            .distinct()  // This ensures that we don't have duplicate projects in the list
	            .collect(Collectors.toList());
	    }
	    
	    public ProjectDTO getProjectById(Integer projectId) throws ProjectNotFoundException {
	        // Retrieve the project by ID from the database
	        Optional<Project> projectOpt = projectRepository.findByProjectID(projectId);
 
	        // If the project is not found, throw ProjectNotFoundException
	        if (projectOpt.isEmpty()|| projectOpt.get().isDeleted()) {
	            throw new ProjectNotFoundException("Project with ID " + projectId + " not found.");
	        }
 
	        // Map the found project to a DTO and return it
	        Project project = projectOpt.get();
	        return new ProjectDTO(
	            project.getProjectID(),
	            project.getProjectName(),
	            project.getDescription(),
	            project.getStartDate(),
	            project.getEndDate(),
	            project.getUser().getUserID()
	        );
	    }
	    
	    public List<ProjectDTO> getProjectsByRoleName(String roleName) throws ProjectNotFoundException {
	        List<Object[]> results = projectRepository.findProjectsByRoleName(roleName);
 
	        if (results.isEmpty()) {
	            throw new ProjectNotFoundException("No projects found for role: " + roleName);
	        }
 
	        return results.stream()
	                      .map(row -> new ProjectDTO(
	                          ((Number) row[0]).intValue(),  // Convert Long to Integer for ProjectID
	                          (String) row[1],              // ProjectName
	                          (String) row[2],              // Description
	                          ((java.sql.Date) row[3]).toLocalDate(), // Convert java.sql.Date to LocalDate for StartDate
	                          ((java.sql.Date) row[4]).toLocalDate(), // Convert java.sql.Date to LocalDate for EndDate
	                          ((Number) row[5]).intValue()  // Convert Long to Integer for UserID
	                      ))
	                      .collect(Collectors.toList());
	    }

}
