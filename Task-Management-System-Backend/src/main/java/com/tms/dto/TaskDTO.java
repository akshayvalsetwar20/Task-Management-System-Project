package com.tms.dto;
 
import java.time.LocalDate;
 
import com.tms.model.Project;
import com.tms.model.User;
 
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
 
public class TaskDTO {
	private Integer taskId;
	@NotBlank(message = "Task name cannot be empty")
    @Size(min = 3, max = 100, message = "Task name must be between 3 and 100 characters")
	private String taskName;
 
	@Size(max = 500, message = "Description must not exceed 500 characters")
	private String description;
 
	@FutureOrPresent(message = "Due date must be today or in the future")
	private LocalDate dueDate;
 
	private String priority;
 
	@NotNull(message = "Task status cannot be null")
	private String status;
 
	@NotNull(message = "Project cannot be null")
	private Integer projectId;
 
	@NotNull(message = "User cannot be null")
	private Integer userId;
 
	// Default constructor
	public TaskDTO() {
		super();
	}
 
	public TaskDTO(Integer taskId, String taskName, String description, LocalDate dueDate, String priority,
			String status, Integer projectId, Integer userId) {
		super();
		this.taskId = taskId;
		this.taskName = taskName;
		this.description = description;
		this.dueDate = dueDate;
		this.priority = priority;
		this.status = status;
		this.projectId = projectId;
		this.userId = userId;
	}
 
	// Getters and Setters
	public Integer getTaskId() {
		return taskId;
	}
 
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
 
	public String getTaskName() {
		return taskName;
	}
 
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
 
	public String getDescription() {
		return description;
	}
 
	public void setDescription(String description) {
		this.description = description;
	}
 
	public LocalDate getDueDate() {
		return dueDate;
	}
 
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
 
	public String getPriority() {
		return priority;
	}
 
	public void setPriority(String priority) {
		this.priority = priority;
	}
 
	public String getStatus() {
		return status;
	}
 
	public void setStatus(String status) {
		this.status = status;
	}
 
	public Integer getProjectId() {
		return projectId;
	}
 
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
 
	public Integer getUserId() {
		return userId;
	}
 
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
 
 
}