package com.tms.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tms.model.Task;
import com.tms.model.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{
	
	Long countByUser(User user);
	 
	List<Task> findByStatusIgnoreCase(String string);

	List<Task> findByIsDeletedFalse();

	List<Task> findByDueDateBeforeAndIsDeletedFalse(LocalDate date);
 
	List<Task> findByDueDateAfterAndIsDeletedFalse(LocalDate date);

	List<Task> findByPriorityAndStatusAndIsDeletedFalse(String priority, String status);
 
	List<Task> findByUserUserIDAndStatusAndIsDeletedFalse(Integer userID, String status);
 
	@Query("SELECT t FROM Task t JOIN TaskCategory tc ON t.taskId = tc.task.taskId "
			+ "JOIN Category c ON tc.category.categoryId = c.categoryId "
			+ "WHERE c.categoryId = :categoryId AND t.isDeleted = false")
	List<Task> findByCategoryIdAndIsDeletedFalse(@Param("categoryId") Integer categoryId);
 
	Optional<Task> findByTaskIdAndIsDeletedFalse(Integer taskId);
 
	List<Task> findByPriority(String string);

}
