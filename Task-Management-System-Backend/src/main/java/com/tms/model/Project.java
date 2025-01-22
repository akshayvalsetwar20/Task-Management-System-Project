package com.tms.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
 
@Entity
@Table(name = "project")
public class Project {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    @Column(name = "projectid")
    private Integer projectID;
 
    @Column(name = "projectname")
    private String projectName;
 
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
 
    @Column(name = "startdate")
    private LocalDate startDate;
 
    @Column(name = "enddate")
    private LocalDate endDate;
 
//    @Column(name = "status")
//    private String status;
 
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userid")
    private User user;
  
 
//  // Flag for soft delete
  @Column(name = "is_deleted", columnDefinition = "boolean default false")
  private boolean isDeleted = false;


	// Default constructor
    public Project() {
    }
 
    public Project(String projectName, String description, LocalDate startDate, LocalDate endDate, 
			User user) {
		super();
		this.projectName = projectName;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.user = user;
	}
 
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
 
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
 
    public User getUser() {
        return user;
    }
 
    public void setUser(User user) {
        this.user = user;
    }
    public boolean isDeleted() {
	return isDeleted;
}

    public void setDeleted(boolean isDeleted) {
	this.isDeleted = isDeleted;
}

}

