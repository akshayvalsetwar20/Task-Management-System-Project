package com.tms.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.tms.dto.TaskDTO;
import com.tms.service.TaskFrontendService;

@Controller
@RequestMapping("/admin/dashboard/tasks")
public class TaskFrontendController {

	@Autowired
	private TaskFrontendService taskService;

	@Autowired
	RestTemplate restTemplate;

	// Show form to create a new task
	@GetMapping("/create-task")
	public String showCreateTaskForm(Model model) {
		model.addAttribute("task", new TaskDTO());
		return "create_task";
	}

	@PostMapping("/save-task")
	public String saveTask(@ModelAttribute("task") TaskDTO taskDTO, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "create_task"; // If there are validation errors, show the form again
		}

		// Call the service to save the task
		taskService.createTask(taskDTO);

		// Redirect based on the task's due date
		LocalDate dueDate = taskDTO.getDueDate();
		LocalDate currentDate = LocalDate.now();
		LocalDate dueSoonDate = currentDate.plusDays(3);

		// Determine whether the task is overdue or due soon
		if (dueDate.isBefore(currentDate)) {
			return "redirect:/admin/dashboard/tasks/overdue-tasks"; // Overdue task
		} else if (dueDate.isBefore(dueSoonDate)) {
			return "redirect:/admin/dashboard/tasks/due-soon"; // Due soon task
		}

		return "redirect:/admin/dashboard/tasks/overdue-tasks"; // Default to overdue tasks if not due soon
	}


    // Load the update task form with existing data
    @GetMapping("/update/{taskId}")
    public String showUpdateTaskForm(@PathVariable Integer taskId, Model model) {
        TaskDTO task = taskService.getTaskById(taskId);
        model.addAttribute("task", task);
        return "update-task"; // HTML template name
    }

    // Handle the form submission for task update
    @PostMapping("/update/{taskId}")
    public String updateTask(@PathVariable Integer taskId, @ModelAttribute("task") TaskDTO taskDTO) {
        taskService.updateTask(taskId, taskDTO);
        return "redirect:/admin/dashboard/tasks/overdue-tasks"; // Redirect to task list or relevant page
    }


	// Show overdue tasks
	@GetMapping("/overdue-tasks")
	public String getOverdueTasks(Model model) {
		model.addAttribute("tasks", taskService.getOverdueTasks());
		return "task_list"; // You can render overdue tasks here
	}

	// Show tasks by priority and status
	@GetMapping("/priority/status")
	public String searchTasksByPriorityAndStatus(@RequestParam String priority, @RequestParam String status,
			Model model) {
		List<TaskDTO> tasks = taskService.getTasksByPriorityAndStatus(priority, status);
		if (tasks.isEmpty()) {
			model.addAttribute("errorMessage", "No tasks found for the specified criteria.");
		} else {
			model.addAttribute("tasks", tasks);
		}
		return "search_results"; // Render the results on a new page
	}

//	// Show tasks due soon
	@GetMapping("/due-soon")
	public String getTasksDueSoon(Model model) {
		model.addAttribute("tasks", taskService.getTasksDueSoon());
		return "task_list";
	}

	@GetMapping("/tasks/delete/{taskId}")
	public String softDeleteTask(@PathVariable Integer taskId) {
		taskService.softDeleteTask(taskId); // Call the service to soft delete the task
		return "redirect:/overdue-tasks"; // Redirect to the tasks list after soft deletion
	}

}
