package com.tms.dto;

import com.tms.model.Category;
import com.tms.model.Task;
import jakarta.validation.constraints.NotNull;

public class TaskCategoryDTO {

    @NotNull(message = "Task ID must not be null")
    private Integer taskId;

    @NotNull(message = "Category ID must not be null")
    private Integer categoryId;

    private Task task;

    private Category category;

    // Constructors
    public TaskCategoryDTO() {}

    public TaskCategoryDTO(Integer taskId, Integer categoryId, Task task, Category category) {
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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
