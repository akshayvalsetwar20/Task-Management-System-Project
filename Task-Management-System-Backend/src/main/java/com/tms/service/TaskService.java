package com.tms.service;
 
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.tms.dto.TaskDTO;
import com.tms.exception.TaskException;
import com.tms.model.Project;
import com.tms.model.Task;
import com.tms.model.User;
import com.tms.repository.ProjectRepository;
import com.tms.repository.TaskRepository;
import com.tms.repository.UserRepository;
 
@Service
public class TaskService implements TaskServiceImpl{
 
	@Autowired
	private TaskRepository taskRepository;
 
	@Autowired
	private UserRepository userRepository;
 
	@Autowired
	private ProjectRepository projectRepository;
 
	// Convert Task entity to TaskDTO
	private TaskDTO convertToDTO(Task task) {
		return new TaskDTO(task.getTaskId(), task.getTaskName(), task.getDescription(), task.getDueDate(),
				task.getPriority(), task.getStatus(), task.getProject().getProjectID(), task.getUser().getUserID());
	}
 
	// Convert TaskDTO to Task entity
	private Task convertToEntity(TaskDTO taskDTO) {
		Task task = new Task();
		task.setTaskName(taskDTO.getTaskName());
		task.setDescription(taskDTO.getDescription());
		task.setDueDate(taskDTO.getDueDate());
		task.setPriority(taskDTO.getPriority());
		task.setStatus(taskDTO.getStatus());
 
		// Fetch the Project by ID
		Optional<Project> project = projectRepository.findByProjectID(taskDTO.getProjectId());
		if (project.isEmpty()) {
			throw new TaskException("CREATEFAIL", "Project with ID " + taskDTO.getProjectId() + " does not exist.");
		}
		task.setProject(project.get());
 
		// Fetch the User by ID
		Optional<User> user = userRepository.findById(taskDTO.getUserId());
		if (user.isEmpty()) {
			throw new TaskException("CREATEFAIL", "User with ID " + taskDTO.getUserId() + " does not exist.");
		}
		task.setUser(user.get());
 
		return task;
	}
 
	// Create a new task using TaskDTO
	public void createTask(TaskDTO taskDTO) {
		Task task = convertToEntity(taskDTO);
		taskRepository.save(task);
	}
 
	// Get overdue tasks
	public List<TaskDTO> getOverdueTasks() {
		return taskRepository.findByDueDateBeforeAndIsDeletedFalse(LocalDate.now()).stream().map(this::convertToDTO)
				.collect(Collectors.toList());
	}
 
	// Get tasks by priority and status
	public List<TaskDTO> getTasksByPriorityAndStatus(String priority, String status) {
		return taskRepository.findByPriorityAndStatusAndIsDeletedFalse(priority, status).stream()
				.map(this::convertToDTO).collect(Collectors.toList());
	}
 
	// Get tasks due soon (within 7 days)
	public List<TaskDTO> getTasksDueSoon() {
		return taskRepository.findByDueDateAfterAndIsDeletedFalse(LocalDate.now().plusDays(7)).stream()
				.map(this::convertToDTO).collect(Collectors.toList());
	}
 
	// Get tasks by user and status
	public List<TaskDTO> getTasksByUserAndStatus(Integer userID, String status) {
		return taskRepository.findByUserUserIDAndStatusAndIsDeletedFalse(userID, status).stream()
				.map(this::convertToDTO).collect(Collectors.toList());
	}
 
	// Get tasks by category
	public List<TaskDTO> getTasksByCategoryId(Integer categoryId) {
		List<Task> catList = taskRepository.findByCategoryIdAndIsDeletedFalse(categoryId);
		if (catList.isEmpty()) {
			throw new TaskException("GETFAILS", "Task with given category id is not found");
		}
		return catList.stream().map(this::convertToDTO).collect(Collectors.toList());
	}
 
	// Update an existing task using TaskDTO
	public void updateTask(Integer taskId, TaskDTO updatedTaskDTO) {
		Task existingTask = taskRepository.findById(taskId)
				.orElseThrow(() -> new TaskException("UPDTFAILS", "Task doesn't exist"));
 
		// Update fields from DTO
		existingTask.setTaskName(updatedTaskDTO.getTaskName());
		existingTask.setDescription(updatedTaskDTO.getDescription());
		existingTask.setDueDate(updatedTaskDTO.getDueDate());
		existingTask.setPriority(updatedTaskDTO.getPriority());
		existingTask.setStatus(updatedTaskDTO.getStatus());
 
		// Update projectId directly
		Optional<Project> project = projectRepository.findByProjectID(updatedTaskDTO.getProjectId());
		if (project.isEmpty()) {
			throw new TaskException("UPDATEFAIL",
					"Project with ID " + updatedTaskDTO.getProjectId() + " does not exist.");
		}
		existingTask.setProject(project.get());
 
		// Update userId directly
		Optional<User> user = userRepository.findById(updatedTaskDTO.getUserId());
		if (user.isEmpty()) {
			throw new TaskException("UPDATEFAIL", "User with ID " + updatedTaskDTO.getUserId() + " does not exist.");
		}
		existingTask.setUser(user.get());
 
		taskRepository.save(existingTask);
	}
 
	// Delete a task (soft delete)
	public void deleteTask(Integer taskId) {
		Task existingTask = taskRepository.findById(taskId)
				.orElseThrow(() -> new TaskException("DLTFAILS", "Task doesn't exist"));
 
		existingTask.setDeleted(true); // Mark as deleted
		taskRepository.save(existingTask);
	}

	// Get task by ID
	public TaskDTO getTaskById(Integer taskId) {
	    Task task = taskRepository.findById(taskId)
	            .orElseThrow(() -> new TaskException("GETFAIL", "Task with ID " + taskId + " does not exist."));
	    return convertToDTO(task);
	}

}