package com.tms.controller;

import com.tms.dto.ApiResponse;
//import com.tms.dto.ProjectDTO;
import com.tms.dto.ProjectFDTO;
import com.tms.service.ProjectFrontendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin/dashboard/projects")
public class ProjectFrontendController {

    @Autowired
    private ProjectFrontendService projectService;

    // Display all projects
    @GetMapping
    public String projectPage(Model model) {
        List<ProjectFDTO> projects = projectService.getOngoingProjects();
        model.addAttribute("project", projects);
        return "project";
    }
    // Display the Project Creation Form
    @GetMapping("/create-project")
    public String showCreateProjectForm(Model model) {
        model.addAttribute("project", new ProjectFDTO(null, null, null, null, null, null));  // Create an empty Project object for the form
        return "create-Project";  // The HTML page to render
    }
    // Create a new project and redirect back to project list
    @PostMapping("/create")
    public String createProject(@ModelAttribute("project") ProjectFDTO projectDTO, Model model) {
        projectService.createProject(projectDTO);
        model.addAttribute("message", "Project created successfully!");
        return "redirect:/admin/dashboard/projects";
    }
    // Filter projects by date range and display the result
    @GetMapping("/filter-by-date")
    public String filterProjectsByDate(@RequestParam("startDate") String startDate,
                                       @RequestParam("endDate") String endDate,
                                       Model model) {
        List<ProjectFDTO> filteredProjects = projectService.getProjectsByDateRange(startDate, endDate);
        model.addAttribute("projects", filteredProjects);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "project";  // Render the same page with filtered results
    }
//    // Method to filter projects by status
//    @GetMapping("/filter-by-status-action")
//    public String filterProjectsByStatus(@RequestParam("status") String status, Model model) {
//        List<ProjectFDTO> filteredProjects = projectService.getProjectsByStatus(status);
//        model.addAttribute("projects", filteredProjects);
//        model.addAttribute("status", status); // Add status to display in the page
//        return "project";  // Return the page to display filtered projects
//    }
    // Method to get projects by project id
    @GetMapping("/byprojectId")
    public String filterProjectsById(@RequestParam("projectId") Long projectId, Model model) {
        ProjectFDTO filteredProject = projectService.getProjectsByProjectID(projectId);
        model.addAttribute("project", filteredProject);
        return "project-by-id";  // Return the page to display filtered projects
    }	
    
    
    @GetMapping("/update/{projectId}")
    public String showUpdateForm(@PathVariable("projectId") Integer projectId, Model model) {
        // Fetch the project using the frontend service
        ProjectFDTO project = projectService.getProjectsByProjectID1(projectId);

        if (project == null) {
            model.addAttribute("message", "Project not found");
            return "error"; // Return an error page if project is not found
        }

        model.addAttribute("project", project);
        return "update-projects"; // Return the view for updating the project
    }
    
    // Handle the form submission for updating the project
    @PostMapping("/update/{projectId}")
    public String updateProject(@PathVariable("projectId") Integer projectId,
                                @RequestParam("projectName") String projectName,
                                @RequestParam("description") String description,
                                @RequestParam("startDate") String startDate,  // It will be in yyyy-MM-dd format
                                @RequestParam("endDate") String endDate,  // It will be in yyyy-MM-dd format
                                @RequestParam("userId") Integer userId,
                                Model model) {
        try {
            // Convert the startDate and endDate from String to LocalDate
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);

            // Create the updated project DTO
            ProjectFDTO updatedProject = new ProjectFDTO(projectId, projectName, description, start, end, userId);

            // Call the service to update the project
            ApiResponse response = projectService.updateProject(updatedProject);

            if (response.getCode().equals("UPDATESUCCESS")) {
                model.addAttribute("message", response.getMessage());
                return "redirect:/admin/dashboard/projects"; // Success page
            } else {
                model.addAttribute("errorMessage", response.getMessage());
                return "error"; // Error page
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating the project.");
            return "error";
        }
    }
 // Method to handle soft delete of a project (using POST)
    @PostMapping("/delete/{projectId}")
    public String deleteProject(@PathVariable Integer projectId, Model model) {
        try {
            projectService.deleteProject(projectId);  // Soft delete the project
            model.addAttribute("message", "Project deleted successfully.");
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());  // Handle error case
        }
        return "project";  // Redirect back to project details page
    }
    
    @GetMapping("/high-priority-tasks")
    public String getHighPriorityProjects(Model model) {
        // Fetch high-priority projects from the service
        List<ProjectFDTO> highPriorityProjects = projectService.getHighPriorityProjects();
        
        // Add the list of projects to the model
        model.addAttribute("highPriorityProjects", highPriorityProjects);
        
        // If there are no high-priority projects, we can add a message
        if (highPriorityProjects == null || highPriorityProjects.isEmpty()) {
            model.addAttribute("message", "No high-priority projects available at the moment.");
        }
        
        // Return the view (Thymeleaf template)
        return "high-priority"; // Ensure the template is named 'high-priority.html'
    }
    // GET: Fetch ongoing projects and display them in the view
    @GetMapping("/ongoing-projects")
    public String getOngoingProjects(Model model) {
        try {
            // Fetch ongoing projects using the service
            List<ProjectFDTO> ongoingProjects = projectService.getOngoingProjects();
            model.addAttribute("ongoingProjects", ongoingProjects);
            return "ongoing-projects"; // Thymeleaf template for ongoing projects
        } catch (Exception e) {
            // Handle case where no ongoing projects are found
            model.addAttribute("message", "No ongoing projects found.");
            return "ongoing-projects"; // Return the same view with a message
        }
    }

}
