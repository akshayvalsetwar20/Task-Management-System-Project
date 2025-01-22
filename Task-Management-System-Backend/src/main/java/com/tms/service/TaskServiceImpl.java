package com.tms.service;

import java.util.List;

import com.tms.dto.TaskDTO;

public interface TaskServiceImpl {
	
	void createTask(TaskDTO taskDTO);

    List<TaskDTO> getOverdueTasks();

    List<TaskDTO> getTasksByPriorityAndStatus(String priority, String status);

    List<TaskDTO> getTasksDueSoon();

    List<TaskDTO> getTasksByUserAndStatus(Integer userID, String status);

    List<TaskDTO> getTasksByCategoryId(Integer categoryId);

    void updateTask(Integer taskId, TaskDTO updatedTaskDTO);

    void deleteTask(Integer taskId);

    TaskDTO getTaskById(Integer taskId);

}
