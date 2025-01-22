package com.tms.model;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TaskCategoryId implements Serializable{
	
	@Column(name = "taskid") 
    private Integer taskId;

    @Column(name = "categoryid")
    private Integer categoryId;

    // Default constructor
    public TaskCategoryId() {
    }

    public TaskCategoryId(Integer taskId, Integer categoryId) {
        this.taskId = taskId;
        this.categoryId = categoryId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskCategoryId that = (TaskCategoryId) o;
        return Objects.equals(taskId, that.taskId) && Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, categoryId);
    }

}
