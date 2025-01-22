package com.tms.service;

import com.tms.dto.UserRoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

@Service
public class UserRoleFrontendService {

    private static final String BASE_URL = "http://localhost:9091/api/userrole"; // Replace with your backend API URL

    @Autowired
    private RestTemplate restTemplate;

    // Fetch all user roles
    public List<UserRoleDTO> getAllUserRoles() {
        ResponseEntity<UserRoleDTO[]> response = restTemplate.exchange(
            BASE_URL + "/all", HttpMethod.GET, null, UserRoleDTO[].class);
        return Arrays.asList(response.getBody());
    }
    // Save a new user role
    public void saveUserRole(UserRoleDTO userRoleDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserRoleDTO> entity = new HttpEntity<>(userRoleDTO, headers);

        restTemplate.exchange(
            BASE_URL + "/post", HttpMethod.POST, entity, Void.class);
    }

    // Update an existing user role
    public void updateUserRole(int userRoleId, UserRoleDTO updatedUserRoleDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserRoleDTO> entity = new HttpEntity<>(updatedUserRoleDTO, headers);

        restTemplate.exchange(
            BASE_URL + "/update/" + userRoleId, HttpMethod.PUT, entity, Void.class);
    }
    
    public UserRoleDTO getUserRoleById(int userRoleId) {
        try {
            // Sending GET request to the external API to fetch user role by ID
            ResponseEntity<UserRoleDTO> response = restTemplate.exchange(
                    BASE_URL + "/" + userRoleId, HttpMethod.GET, null, UserRoleDTO.class);
            return response.getBody(); // Return the fetched data
        } catch (Exception e) {
            throw new RuntimeException("UserRole not found for ID: " + userRoleId, e);
        }
    }

    // Delete a user role
    public void deleteUserRole(int id) {
      restTemplate.delete(BASE_URL + "/delete/" + id);
  }
}