package com.tms.repository;
import com.tms.model.TaskCategory;
import com.tms.model.TaskCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskCategoryRepository extends JpaRepository<TaskCategory, TaskCategoryId>{
	
    Optional<TaskCategory> findById_TaskIdAndId_CategoryId(Integer taskId, Integer categoryId);

    List<TaskCategory> findByTask_TaskId(Integer taskId);

    List<TaskCategory> findByCategory_CategoryId(Integer categoryId);


}
