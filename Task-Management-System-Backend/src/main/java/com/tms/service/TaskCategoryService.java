package com.tms.service;

import com.tms.dto.TaskCategoryDTO;
import com.tms.model.Category;
import com.tms.model.Task;
import com.tms.model.TaskCategory;
import com.tms.model.TaskCategoryId;
import com.tms.repository.CategoryRepository;
import com.tms.repository.TaskCategoryRepository;
import com.tms.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskCategoryService implements TaskCategoryServiceImpl{
	
	@Autowired
    private TaskCategoryRepository taskCategoryRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Method to associate tasks with categories
    public boolean associateTaskWithCategories(List<TaskCategoryDTO> taskCategoryDTOs) {
        for (TaskCategoryDTO taskCategoryDTO : taskCategoryDTOs) {
            // Check if the association already exists
            if (taskCategoryRepository.findById_TaskIdAndId_CategoryId(
                taskCategoryDTO.getTaskId(), taskCategoryDTO.getCategoryId()).isPresent()) {
                return false; // Association already exists
            }
        }

        // Convert DTOs to TaskCategory entities
        List<TaskCategory> taskCategories = taskCategoryDTOs.stream().map(dto -> {
            TaskCategory taskCategory = new TaskCategory();

            // Set the ID for TaskCategory
            taskCategory.setId(new TaskCategoryId(dto.getTaskId(), dto.getCategoryId()));

            // Fetch Task and Category entities
            Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
            Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

            // Set the Task and Category objects
            taskCategory.setTask(task);
            taskCategory.setCategory(category);

            return taskCategory;
        }).collect(Collectors.toList());

        // Save all TaskCategory entities to the repository
        taskCategoryRepository.saveAll(taskCategories);
        return true;
    }

    // Method to get categories for a specific task
    public List<Category> getCategoriesForTask(Integer taskId) {
        List<TaskCategory> taskCategories = taskCategoryRepository.findByTask_TaskId(taskId);
        if (taskCategories.isEmpty()) {
            return null;
        }

        return taskCategories.stream()
                             .map(TaskCategory::getCategory)
                             .collect(Collectors.toList());
    }

    // Method to get tasks for a specific category
    public List<TaskCategoryDTO> getTasksForCategory(Integer categoryId) {
        List<TaskCategory> taskCategories = taskCategoryRepository.findByCategory_CategoryId(categoryId);
        if (taskCategories.isEmpty()) {
            throw new RuntimeException("No tasks found for this category"); // Custom error message for no tasks
        }

        // Convert TaskCategory entities to TaskCategoryDTOs
        return taskCategories.stream().map(taskCategory -> {
            return new TaskCategoryDTO(
                taskCategory.getId().getTaskId(),
                taskCategory.getId().getCategoryId(),
                taskCategory.getTask(),
                taskCategory.getCategory()
            );
        }).collect(Collectors.toList());
    }

}
