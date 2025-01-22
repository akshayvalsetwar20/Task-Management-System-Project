//package com.tms.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import com.tms.dto.UserRolesDTO;
//
//@Service
//public class UserRolesAPIService {
//
//	@Autowired
//	private RestTemplate restTemplate;
//
//	private final String BASE_URL = "http://localhost:9091/api/userroles"; // Your backend URL
//
//	// Assign role to a user by calling the backend API
//	public void assignRoleToUser(UserRolesDTO userRolesDTO) {
//		String url = BASE_URL + "/assign";
//
//		restTemplate.postForObject(url, userRolesDTO, UserRolesDTO.class);
//	}
//
//	// Get all user roles from the backend API
//	public List<UserRolesDTO> getAllUserRoles() {
//		String url = BASE_URL + "/all";
//
//		ResponseEntity<List<UserRolesDTO>> response = restTemplate.exchange(url,
//				org.springframework.http.HttpMethod.GET, null,
//				new org.springframework.core.ParameterizedTypeReference<List<UserRolesDTO>>() {
//				});
//
//		return response.getBody();
//	}
//
//	// Get user roles by user ID from the backend API
//	public List<UserRolesDTO> getUserRolesByUserId(Integer userId) {
//		String url = BASE_URL + "/user/" + userId;
//
//		ResponseEntity<List<UserRolesDTO>> response = restTemplate.exchange(url,
//				org.springframework.http.HttpMethod.GET, null,
//				new org.springframework.core.ParameterizedTypeReference<List<UserRolesDTO>>() {
//				});
//
//		return response.getBody();
//	}
//
////
//	public void revokeUserRole(Integer userRoleId, Integer userId) {
//		// Construct the URL
//		String url = BASE_URL + "/revoke/" + userRoleId + "/" + userId;
//
//		restTemplate.delete(url);
//	}
//}


package com.tms.service;

import com.tms.dto.UserRolesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserRolesFrontendService {

    private static final String BASE_URL = "http://localhost:9091/api/userroles"; // Your backend URL

    @Autowired
    private RestTemplate restTemplate;

    // Assign role to a user by calling the backend API
    public void assignRoleToUser(UserRolesDTO userRolesDTO) {
        try {
            String url = BASE_URL + "/assign";
            restTemplate.postForObject(url, userRolesDTO, UserRolesDTO.class);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("User with "+userRolesDTO.getRoleName()+" Role already exists.");
        } catch (HttpServerErrorException e) {
            throw new RuntimeException("Server error while assigning role to user.");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while assigning role to user.");
        }
    }

    // Get all user roles from the backend API
    public List<UserRolesDTO> getAllUserRoles() {
        try {
            String url = BASE_URL + "/all";
            ResponseEntity<List<UserRolesDTO>> response = restTemplate.exchange(url,
                    org.springframework.http.HttpMethod.GET, null,
                    new org.springframework.core.ParameterizedTypeReference<List<UserRolesDTO>>() {});

            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error fetching all user roles");
        } catch (HttpServerErrorException e) {
            throw new RuntimeException("Server error while fetching all user roles.");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while fetching all user roles.");
        }
    }

    // Get user roles by user ID from the backend API
    public List<UserRolesDTO> getUserRolesByUserId(Integer userId) {
        try {
            String url = BASE_URL + "/user/" + userId;
            ResponseEntity<List<UserRolesDTO>> response = restTemplate.exchange(url,
                    org.springframework.http.HttpMethod.GET, null,
                    new org.springframework.core.ParameterizedTypeReference<List<UserRolesDTO>>() {});

            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error fetching user roles for user ID: "+userId);
        } catch (HttpServerErrorException e) {
            throw new RuntimeException("Server error while fetching user roles for user ID: " + userId);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while fetching user roles for user ID: " + userId);
        }
    }

    // Revoke a user role by calling the backend API
    public void revokeUserRole(Integer userRoleId, Integer userId) {
        try {
            String url = BASE_URL + "/revoke/" + userRoleId + "/" + userId;
            restTemplate.delete(url);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error revoking role for user with ID: " + userId);
        } catch (HttpServerErrorException e) {
            throw new RuntimeException("Server error while revoking role for user with ID: " + userId);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while revoking role for user with ID: " + userId);
        }
    }
}

