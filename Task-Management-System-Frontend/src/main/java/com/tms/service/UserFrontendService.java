package com.tms.service;

import com.tms.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UserFrontendService {

	private static final String BASE_URL = "http://localhost:9091/api/users";

	@Autowired
	private RestTemplate restTemplate;

	// Fetch all users
	public List<UserDTO> getAllUsers() {
		try {
			UserDTO[] users = restTemplate.getForObject(BASE_URL + "/all", UserDTO[].class);
			return Arrays.asList(users);
		} catch (HttpClientErrorException e) {
			throw new RuntimeException("Error fetching users from the backend: " + e.getResponseBodyAsString(), e);
		} catch (HttpServerErrorException e) {
			throw new RuntimeException("Server error while fetching users from the backend.", e);
		} catch (Exception e) {
			throw new RuntimeException("An unexpected error occurred while fetching users.", e);
		}
	}

	// Fetch user by ID
	public UserDTO getUserById(int userId) {
		try {
			String url = BASE_URL + "/" + userId;
			return restTemplate.getForObject(url, UserDTO.class);
		} catch (HttpClientErrorException.NotFound e) {
			throw new RuntimeException("User not found with ID: " + userId);
		} catch (HttpClientErrorException e) {
			throw new RuntimeException(
					"Error fetching user with ID: " + userId + ". Error: " + e.getResponseBodyAsString(), e);
		} catch (HttpServerErrorException e) {
			throw new RuntimeException("Server error while fetching user with ID: " + userId, e);
		} catch (Exception e) {
			throw new RuntimeException("Unexpected error while fetching user with ID: " + userId, e);
		}
	}

	// Create a new user
	public void createUser(UserDTO userDTO) {
		try {
			restTemplate.postForObject(BASE_URL + "/post", userDTO, UserDTO.class);
		} catch (Exception e) {
			throw new RuntimeException("Error creating user. " + e.getMessage(), e);
		}
	}

	// Update user
	public void updateUser(Integer userId, UserDTO userDTO) {
		try {
			String url = BASE_URL + "/update/" + userId;
			restTemplate.put(url, userDTO);
		} catch (Exception e) {
			throw new RuntimeException("Error updating user. " + e.getMessage(), e);
		}
	}

	// Delete a user
	public void deleteUser(int userId) {
		try {
			String url = BASE_URL + "/delete/" + userId;
			restTemplate.delete(url);
		} catch (Exception e) {
			throw new RuntimeException("Error deleting user. " + e.getMessage(), e);
		}
	}

//	// Search users by full name
//	public List<UserDTO> searchUsersByName(String name) {
//		try {
//			UserDTO[] users = restTemplate.getForObject(BASE_URL + "/search/" + name, UserDTO[].class);
//			return Arrays.asList(users);
//		} catch (HttpClientErrorException.NotFound e) {
//			throw new RuntimeException("No users found with the name: " + name);
//		} catch (HttpClientErrorException e) {
//			throw new RuntimeException(
//					"Error searching users by name: " + name + ". Error: " + e.getResponseBodyAsString(), e);
//		} catch (HttpServerErrorException e) {
//			throw new RuntimeException("Server error while searching users by name: " + name, e);
//		} catch (Exception e) {
//			throw new RuntimeException("Unexpected error while searching users by name: " + name, e);
//		}
//	}

	// Search users by email domain
	public List<UserDTO> searchUsersByEmailDomain(String domain) {
		try {
			UserDTO[] users = restTemplate.getForObject(BASE_URL + "/email-domain/" + domain, UserDTO[].class);
			return Arrays.asList(users);
		} catch (HttpClientErrorException e) {
			throw new RuntimeException("Error searching users by email domain: " + domain + e.getResponseBodyAsString(),
					e);
		} catch (HttpServerErrorException e) {
			throw new RuntimeException("Server error while searching users by email domain: " + domain, e);
		} catch (Exception e) {
			throw new RuntimeException("Unexpected error while searching users by email domain: " + domain, e);
		}
	}
}
