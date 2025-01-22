package com.tms.controller;
 
import com.tms.dto.ApiResponse;
import com.tms.dto.UserDTO;
import com.tms.model.User;
import com.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import jakarta.validation.Valid;
import java.util.List;
 
@RestController
@RequestMapping("/api/users")
public class UserController {
 
	@Autowired
	UserService userService;
 
	// Create a new user
	@PostMapping("/post")
	public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserDTO userDTO) {
		userService.createUser(userDTO);
		ApiResponse apiResponse = new ApiResponse("POSTSUCCESS", "User added successfully");
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}
 
	// Get a list of all users
	@GetMapping("/all")
	public List<UserDTO> getAllUsers() {
		return userService.getAllUsers();
	}
 
	// Get a user by userId
	@GetMapping("/{userId}")
	public UserDTO getUserById(@PathVariable int userId) {
		return userService.getUserById(userId);
	}
 
	// Get users by email domain
	@GetMapping("/email-domain/{domain}")
	public List<UserDTO> getUsersByEmailDomain(@PathVariable String domain) {
		return userService.getUsersByEmailDomain(domain);
	}
 
	// Search users by full name
	@GetMapping("/search/{name}")
	public ResponseEntity<Object> getUsersByFullName(@PathVariable String name) {
		Object result = userService.searchUserByFullName(name);
 
		// If the result is a list, return it with a 200 OK status
		if (result instanceof List) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
 
		// If it's a single user, return it as a user object with 200 OK status
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
 
	// Get the user with the most tasks
	@GetMapping("/most-tasks")
	public List<UserDTO> getUserWithMostTasks() {
		return userService.getUsersWithMostTasks();
	}
 
//	// Authentication endpoint
//	@GetMapping("/authenticate")
//	public ResponseEntity<ApiResponse> authenticateUser(@RequestParam String username, @RequestParam String password) {
//		userService.authenticateUser(username, password);
//		ApiResponse apiResponse = new ApiResponse("AUTHSUCCESS", "User is valid");
//		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
//	}
 
	// Authentication endpoint
	@GetMapping("/authenticate")
	public ResponseEntity<User> authenticateUser(@RequestParam String username, @RequestParam String password) {
		User user = userService.authenticateUser(username, password);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK); // Return the user object
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Return unauthorized if user not found
		}
	}
 
	// Get users who have completed tasks
	@GetMapping("/completed-tasks")
	public List<UserDTO> getUsersWithCompletedTasks() {
		return userService.getUsersWithCompletedTasks();
	}
 
	// Update user details
	@PutMapping("/update/{userId}")
	public ResponseEntity<ApiResponse> updateUser(@Valid @PathVariable int userId,
			@Valid @RequestBody UserDTO userDTO) {
		userService.updateUser(userId, userDTO);
		ApiResponse apiResponse = new ApiResponse("UPDATESUCCESS", "User updated successfully");
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
 
	// Delete user
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable int userId) {
		userService.deleteUser(userId);
		ApiResponse apiResponse = new ApiResponse("DELETESUCCESS", "User deleted successfully");
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
}