package com.tms.service;

import com.tms.dto.UserDTO;
import com.tms.exception.UserAlreadyExistsException;
import com.tms.exception.UserListEmptyException;
import com.tms.exception.UserNotFoundException;
import com.tms.model.User;
import com.tms.repository.TaskRepository;
import com.tms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceImpl{

	@Autowired
	UserRepository userRepository;

	@Autowired
	TaskRepository taskRepository;

	// Create a new user
	public void createUser(UserDTO userDTO) {
		if (userRepository.findByUsername(userDTO.getUsername()).isPresent()
				|| userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
			throw new UserAlreadyExistsException("User already exists");
		}

		User user = new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getEmail(), userDTO.getFullName());
		userRepository.save(user);
	}

	// Get all users
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<User> allExistingUsers = users.stream().filter(user -> !user.isDeleted()).collect(Collectors.toList());

		if (allExistingUsers.isEmpty()) {
			throw new UserListEmptyException("User list is empty");
		}

		return allExistingUsers.stream().map(UserDTO::fromUser).collect(Collectors.toList());
	}

	// Get a user by userId
	public UserDTO getUserById(int userId) {
		Optional<User> userOptional = userRepository.findById(userId);

		if (userOptional.isEmpty() || userOptional.get().isDeleted()) {
			throw new UserNotFoundException("User doesn't exist");
		}

		return UserDTO.fromUser(userOptional.get());
	}

	// Get users by email domain
	public List<UserDTO> getUsersByEmailDomain(String domain) {
		List<User> allUsers = userRepository.findAll();

		List<User> usersList = allUsers.stream().filter(user -> user.getEmail().endsWith("@" + domain))
				.collect(Collectors.toList());

		if (usersList.isEmpty()) {
			throw new UserNotFoundException("User doesn't exist exist");
		}

		return usersList.stream().map(UserDTO::fromUser).collect(Collectors.toList());
	}

	// Search users by full name
	public Object searchUserByFullName(String name) {
		List<User> users = userRepository.findUsersByFullNameIgnoreCase(name);

		if (users.isEmpty() || users.stream().allMatch(User::isDeleted)) {
			throw new UserNotFoundException("User doesn't exist");
		}

		// If only one user is found, return a single user DTO
		if (users.size() == 1) {
			return UserDTO.fromUser(users.get(0));
		}

		// If multiple users are found, return a list of user DTOs
		return users.stream().filter(user -> !user.isDeleted()) // Ensure deleted users are excluded
				.map(UserDTO::fromUser).collect(Collectors.toList());
	}

	// Get users with the most tasks
	public List<UserDTO> getUsersWithMostTasks() {
		List<User> users = userRepository.findAll();

		long maxTaskCount = users.stream().mapToLong(user -> taskRepository.countByUser(user)).max().orElse(0);

		List<User> usersWithMostTasks = users.stream()
				.filter(user -> taskRepository.countByUser(user) == maxTaskCount && !user.isDeleted())
				.collect(Collectors.toList());

		if (usersWithMostTasks.isEmpty()) {
			throw new UserNotFoundException("User doesn't exist");
		}

		return usersWithMostTasks.stream().map(UserDTO::fromUser).collect(Collectors.toList());
	}

	// Authenticate user
	public User authenticateUser(String username, String password) {
		Optional<User> userOptional = userRepository.findByUsername(username);

		if (userOptional.isEmpty() || userOptional.get().isDeleted()
				|| !userOptional.get().getPassword().equals(password)) {
			throw new UserNotFoundException("User doesn't exist exist");
		}
		return userOptional.get();
	}

	// Get users who have completed tasks
	public List<UserDTO> getUsersWithCompletedTasks() {
		List<User> users = taskRepository.findByStatusIgnoreCase("completed").stream().map(task -> task.getUser())
				.distinct().filter(user -> !user.isDeleted()).collect(Collectors.toList());

		if (users.isEmpty()) {
			throw new UserListEmptyException("User list is empty");
		}

		return users.stream().map(UserDTO::fromUser).collect(Collectors.toList());
	}

	// Update user details
	public void updateUser(int userId, UserDTO userDTO) {
		Optional<User> existingUserOptional = userRepository.findById(userId);

		if (existingUserOptional.isEmpty() || existingUserOptional.get().isDeleted()) {
			throw new UserNotFoundException("UPDTFAILS", "User doesn't exist");
		}

		User existingUser = existingUserOptional.get();
		existingUser.setUsername(userDTO.getUsername());
		existingUser.setEmail(userDTO.getEmail());
		existingUser.setFullName(userDTO.getFullName());
		existingUser.setPassword(userDTO.getPassword());

		userRepository.save(existingUser);
	}

	// Delete user (soft delete)
	public void deleteUser(int userId) {
		Optional<User> userOptional = userRepository.findById(userId);

		if (userOptional.isEmpty() || userOptional.get().isDeleted()) {
			throw new UserNotFoundException("DLTFAILS", "User doesn't exist");
		}

		User user = userOptional.get();
		user.setDeleted(true);

		userRepository.save(user);
	}
}