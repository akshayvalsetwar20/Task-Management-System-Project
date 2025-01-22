package com.tms.controller;

import com.tms.dto.ApiResponse;
import com.tms.dto.CategoryDTO;
import com.tms.dto.ProjectFDTO;
import com.tms.dto.TaskCategoryDTO;
import com.tms.service.TaskCategoryFrontendService;
//import com.tms.service.TaskCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/dashboard/taskcategories")
public class TaskCategoryFrontendController {

    @Autowired
    private TaskCategoryFrontendService taskCategoryService;
    
    // Endpoint to display form for associating tasks with categories
    @GetMapping
    public String associateForm(Model model) {
        model.addAttribute("taskCategoryDTO", new TaskCategoryDTO());
        return "taskcategory"; // This will load task-category-associate.html
    }
    // GET: Show the form for associating task with category
    @GetMapping("/create-category")
    public String showTaskCategoryForm(Model model) {
        model.addAttribute("taskCategoryDTO", new TaskCategoryDTO());  // Add an empty DTO to the model
        return "task-category-associate";  // Thymeleaf template
    }
 // Endpoint to handle the form submission and associate task with category
    @PostMapping("/create-category")
    public String associateTaskWithCategories(@ModelAttribute TaskCategoryDTO taskCategoryDTO, Model model) {
        boolean isSuccessful = taskCategoryService.associateTaskWithCategories(List.of(taskCategoryDTO));

        if (isSuccessful) {
            model.addAttribute("message", "Task-category added successfully.");
        } else {
            model.addAttribute("message", "Task-Category already exists.");
        }

        return "task-category-associate"; // Reload the form with the result message
    }
    @GetMapping("associate")
    public String associateForm1(Model model) {
        model.addAttribute("taskCategoryDTO", new TaskCategoryDTO());
        return "taskcategories"; // This will load task-category-associate.html
    }
    
    @GetMapping("/categories")
    public String getCategoriesForTask(@RequestParam("taskId") Integer taskId, Model model) {
        List<CategoryDTO> categories = taskCategoryService.getCategoriesForTask(taskId);

        if (categories == null || categories.isEmpty()) {
            model.addAttribute("message", "No categories found for this task.");
        } else {
            model.addAttribute("categories", categories);
        }

        return "task-categories";  // This will load task-categories.html
    }
    // Show tasks for a specific category
    @GetMapping("/tasks")
    public String getTasksForCategory(@RequestParam("categoryId") Integer categoryId, Model model) {
        List<TaskCategoryDTO> taskCategories = taskCategoryService.getTasksForCategory(categoryId);

        if (taskCategories == null || taskCategories.isEmpty()) {
            model.addAttribute("message", "No tasks found for this category.");
        } else {
            model.addAttribute("taskCategories", taskCategories);
        }

        return "tasks-for-category";  // Thymeleaf template to show tasks for category
    }


}
