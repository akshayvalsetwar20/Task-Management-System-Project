package com.tms.service;

import java.time.LocalDate;
import java.util.List;

import com.tms.dto.ProjectDTO;
import com.tms.exception.HighPriorityTaskEmptyException;
import com.tms.exception.ProjectAlreadyExistsException;
import com.tms.exception.ProjectListEmptyException;
import com.tms.exception.ProjectNotFoundException;

public interface ProjectServiceImpl {
	
	ProjectDTO createProject(ProjectDTO projectDTO) throws ProjectAlreadyExistsException;

    List<ProjectDTO> getOngoingProjects() throws ProjectListEmptyException;

    List<ProjectDTO> getProjectsByDateRange(LocalDate startDate, LocalDate endDate) throws ProjectListEmptyException;

    void deleteProject(Integer projectId) throws ProjectNotFoundException;

    ProjectDTO updateProject(Integer projectId, ProjectDTO updatedProjectDTO) throws ProjectNotFoundException;

    List<ProjectDTO> getProjectsByStatus(String status) throws ProjectListEmptyException;

    List<ProjectDTO> getHighPriorityProjects() throws HighPriorityTaskEmptyException;

    ProjectDTO getProjectById(Integer projectId) throws ProjectNotFoundException;

}
