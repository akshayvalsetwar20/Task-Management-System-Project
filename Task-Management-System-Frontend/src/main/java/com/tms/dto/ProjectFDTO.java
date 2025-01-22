package com.tms.dto;

import java.time.LocalDate;

public class ProjectFDTO {

    private Integer projectID;
    private String projectName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer userId;

    // Constructor
    public ProjectFDTO(Integer projectID, String projectName, String description, LocalDate startDate, LocalDate endDate, Integer userId) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
    }

    // Getters and setters
    public Integer getProjectID() {
        return projectID;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
