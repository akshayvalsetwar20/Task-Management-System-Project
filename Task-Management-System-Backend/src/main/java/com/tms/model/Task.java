package com.tms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
 
@Entity
@Table(name = "task")
public class Task {
 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "taskid")
	private Integer taskId;

	@Column(name = "taskname")
	private String taskName;

	@Column(name = "description")
	private String description;

	@Column(name = "duedate")
	private LocalDate dueDate;

	@Column(name = "priority")
	private String priority;

	@Column(name = "status")
	private String status;

	@Column(nullable = false, name = "is_deleted")
	private boolean isDeleted = false;

	// Relationships
	@ManyToOne
	@JoinColumn(name = "projectid", nullable = true)
	private Project project;

	@ManyToOne
	@JoinColumn(name = "userid", nullable = true)
	private User user;

	// Default constructor
	public Task() {
	}

	// Constructor with all fields
	public Task(String taskName, String description, LocalDate dueDate, String priority, String status) {
		this.taskName = taskName;
		this.description = description;
		this.dueDate = dueDate;
		this.priority = priority;
		this.status = status;
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JsonIgnore
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
