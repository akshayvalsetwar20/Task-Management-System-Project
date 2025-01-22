package com.tms.service;

import java.util.List;

import com.tms.dto.TaskCategoryDTO;
import com.tms.model.Category;

public interface TaskCategoryServiceImpl {
	
	boolean associateTaskWithCategories(List<TaskCategoryDTO> taskCategoryDTOs);

    List<Category> getCategoriesForTask(Integer taskId);

    List<TaskCategoryDTO> getTasksForCategory(Integer categoryId);

}
