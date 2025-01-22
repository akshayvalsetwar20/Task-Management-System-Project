package com.tms.controller;

import com.tms.dto.UserDTO;
import com.tms.service.UserFrontendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/dashboard/users")
public class UserController {

	@Autowired
	private UserFrontendService userService;

	// Display all users
	@GetMapping
	public String userPage(Model model) {
		try {
			List<UserDTO> users = userService.getAllUsers();
			model.addAttribute("users", users);
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}
		return "user";
	}

	// Display the page to create a new user
	@GetMapping("/create")
	public String showCreateUserPage(Model model) {
		model.addAttribute("user", new UserDTO());
		return "user-form";
	}

	@PostMapping("/save")
	public String createUser(@ModelAttribute UserDTO userDTO, Model model) {
		try {
			userService.createUser(userDTO);
			model.addAttribute("message", "User created successfully.");
		} catch (Exception e) {
			model.addAttribute("message", "Error creating user: " + e.getMessage());
		}
		return "redirect:/admin/dashboard/users"; // Redirect to the user listing page after creation
	}

	// Show user details by userID
	@GetMapping("/user")
	public String getUser(@RequestParam("userId") Integer userId, Model model) {
		try {
			UserDTO user = userService.getUserById(userId);
			model.addAttribute("user", user);
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}
		return "user";
	}

	// Show page to edit user details
	@GetMapping("/edit/{userId}")
	public String showEditUserPage(@PathVariable("userId") Integer userId, Model model) {
		try {
			UserDTO user = userService.getUserById(userId);
			model.addAttribute("user", user);
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}
		return "user-update";
	}

	// Handle the form submission to update the user
	@PostMapping("/update")
	public String updateUser(@RequestParam("userId") Integer userId, @ModelAttribute UserDTO userDTO, Model model) {
		try {
			userService.updateUser(userId, userDTO);
			model.addAttribute("successMessage", "User updated successfully!");
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}
		return "redirect:/admin/dashboard/users";
	}

	// Delete a user by userID
	@GetMapping("/delete/{userId}")
	public String deleteUser(@PathVariable("userId") int userId, Model model) {
		try {
			userService.deleteUser(userId);
			model.addAttribute("message", "User deleted successfully!");
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}
		return "redirect:/admin/dashboard/users";
	}

//	// Search users by full name
//	@GetMapping("/search")
//	public String searchUser(@RequestParam("name") String name, Model model) {
//		try {
//			List<UserDTO> users = userService.searchUsersByName(name);
//			if (users.isEmpty()) {
//				model.addAttribute("message", "No users found with the name: " + name);
//			} else {
//				model.addAttribute("users", users);
//			}
//		} catch (Exception e) {
//			model.addAttribute("message", "Error fetching users: " + e.getMessage());
//		}
//		return "user"; // The view name
//	}

	// Search users by email domain
	@GetMapping("/email-domain")
	public String searchUserByEmailDomain(@RequestParam("domain") String domain, Model model) {
		try {
			List<UserDTO> users = userService.searchUsersByEmailDomain(domain);
			model.addAttribute("users", users);
		} catch (Exception e) {
			model.addAttribute("message", "Error: " + e.getMessage());
		}
		return "user";
	}
}
