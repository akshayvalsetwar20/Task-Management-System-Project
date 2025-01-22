package com.tms.service;

import com.tms.dto.NotificationDTO;
import com.tms.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationFrontendService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BACKEND_API_BASE_URL = "http://localhost:9091/api/notifications";

    // Fetch all notifications
    public List<NotificationDTO> getAllNotifications() {
        String url = BACKEND_API_BASE_URL + "/all";
        try {
            // Use ParameterizedTypeReference to handle proper deserialization into List<NotificationDTO>
            ResponseEntity<List<NotificationDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<NotificationDTO>>() {}
            );
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            // Handle error if the API response is an error
            System.err.println("Error occurred while fetching notifications: " + e.getMessage());
            return null;
        }
    }

    // Fetch a single notification by ID
    public Optional<NotificationDTO> getNotificationById(Integer id) {
        String url = BACKEND_API_BASE_URL + "/" + id;
        ResponseEntity<NotificationDTO> response = restTemplate.getForEntity(url, NotificationDTO.class);
        return Optional.ofNullable(response.getBody());
    }

    // Add a new notification
    public ApiResponse addNotification(NotificationDTO notificationDTO) {
        String url = BACKEND_API_BASE_URL + "/add";
        try {
            // Sending a POST request to the backend API with notification data
            ResponseEntity<ApiResponse> response = restTemplate.postForEntity(url, notificationDTO, ApiResponse.class);
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            // Handle any error (e.g., validation errors)
            return new ApiResponse("ERROR", "Error adding notification: " + e.getMessage());
        }
    }


    // Update an existing notification
    public void updateNotification(Integer id, NotificationDTO notificationDTO) {
        String url = BACKEND_API_BASE_URL + "/update/" + id;
        restTemplate.put(url, notificationDTO);
    }

    // Delete a notification (soft delete)
    public void deleteNotification(Integer id) {
        String url = BACKEND_API_BASE_URL + "/delete/" + id;
        restTemplate.delete(url);
    }
}
