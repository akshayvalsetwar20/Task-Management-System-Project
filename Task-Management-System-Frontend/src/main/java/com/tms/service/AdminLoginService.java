package com.tms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

import com.tms.dto.UserDTO;
import com.tms.dto.UserRolesDTO;

import java.util.List;

@Service
public class AdminLoginService {

	@Autowired
	private RestTemplate restTemplate;

	// Method to authenticate the user using username and password
	public UserDTO authenticateUser(String username, String password) {
		String authUrl = "http://localhost:9091/api/users/authenticate?username=" + username + "&password=" + password;

		try {
			UserDTO userResponse = restTemplate.getForObject(authUrl, UserDTO.class);
			if (userResponse == null) {
				System.out.println("Authentication failed: Invalid username or password");
				return null;
			}
			System.out.println(userResponse.getFullName());
			return userResponse;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	// Method to fetch user roles and check for "Admin" role
	public String getUserRoles(int userId) {
		String roleUrl = "http://localhost:9091/api/userroles/user/" + userId;

		try {
			ResponseEntity<List<UserRolesDTO>> responseEntity = restTemplate.exchange(roleUrl,
					org.springframework.http.HttpMethod.GET, null,
					new ParameterizedTypeReference<List<UserRolesDTO>>() {
					});

			List<UserRolesDTO> roleResponse = responseEntity.getBody();

			// Check if the user has any roles assigned
			if (roleResponse == null || roleResponse.isEmpty()) {
				return "The user has no roles assigned.";
			}

			for (UserRolesDTO role : roleResponse) {
				if ("Admin".equals(role.getRoleName())) {
					return "admin";
				}
			}

			return "The user does not have the 'Admin' role.";
		} catch (Exception ex) {
			ex.printStackTrace();
			return "An error occurred while fetching user roles.";
		}
	}
}
