package com.tms.dto;

import com.tms.dto.CategoryDTO;
import com.tms.dto.TaskDTO;

public class TaskCategoryDTO {

    
    private Integer taskId;


    private Integer categoryId;

    private TaskDTO task;

    private CategoryDTO category;

    // Constructors
    public TaskCategoryDTO() {}

    public TaskCategoryDTO(Integer taskId, Integer categoryId, TaskDTO task, CategoryDTO category) {
        this.taskId = taskId;
        this.categoryId = categoryId;
        this.task = task;
        this.category = category;
    }

    // Getters and Setters
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public TaskDTO getTask() {
        return task;
    }

    public void setTask(TaskDTO task) {
        this.task = task;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }
}
