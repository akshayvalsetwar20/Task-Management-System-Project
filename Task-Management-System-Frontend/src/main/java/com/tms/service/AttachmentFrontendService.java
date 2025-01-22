package com.tms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import com.tms.dto.ApiResponse;
import com.tms.dto.AttachmentDTO;

import java.util.List;

@Service
public class AttachmentFrontendService {

    private static final String API_BASE_URL = "http://localhost:9091/api/attachments";

    @Autowired
    private RestTemplate restTemplate;

    // Add Attachment
    public ApiResponse addAttachment(AttachmentDTO attachmentDTO) {
        String url = API_BASE_URL + "/post";
        try {
            ResponseEntity<ApiResponse> response = restTemplate.postForEntity(url, attachmentDTO, ApiResponse.class);
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            // Handle any error (e.g., validation errors)
            return new ApiResponse("ERROR", "Error adding attachment: " + e.getMessage());
        }
    }

    // Get All Attachments (excluding soft-deleted ones)
    public List<AttachmentDTO> getAllAttachments() {
        String url = API_BASE_URL + "/all";
        try {
            // Use ParameterizedTypeReference to ensure proper deserialization into List<AttachmentDTO>
            ResponseEntity<List<AttachmentDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AttachmentDTO>>() {}
            );
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            // Handle error if the API response is an error
            System.err.println("Error occurred while fetching attachments: " + e.getMessage());
            return null;
        }
    }


    // Get Attachment by ID (including check for soft deletion)
    public AttachmentDTO getAttachmentById(Integer attachmentId) {
        String url = API_BASE_URL + "/" + attachmentId;
        try {
            ResponseEntity<AttachmentDTO> response = restTemplate.getForEntity(url, AttachmentDTO.class);
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            // Handle errors for non-existing or deleted attachments
            return null;
        }
    }

    // Update Attachment (check if it's not deleted)
    public ApiResponse updateAttachment(Integer attachmentId, AttachmentDTO attachmentDTO) {
        String url = API_BASE_URL + "/update/" + attachmentId;
        try {
            restTemplate.put(url, attachmentDTO);
            return new ApiResponse("UPDATESUCCESS", "Attachment updated successfully.");
        } catch (HttpStatusCodeException e) {
            // Handle update errors (e.g., attachment not found or already deleted)
            return new ApiResponse("ERROR", "Failed to update attachment.");
        }
    }

    // Delete Attachment (soft delete, do not remove from DB)
    public ApiResponse deleteAttachment(Integer attachmentId) {
        String url = API_BASE_URL + "/delete/" + attachmentId;
        try {
            restTemplate.delete(url);
            return new ApiResponse("DELETESUCCESS", "Attachment marked as deleted successfully.");
        } catch (HttpStatusCodeException e) {
            // Handle errors (e.g., attachment not found or already deleted)
            return new ApiResponse("ERROR", "Failed to mark attachment as deleted.");
        }
    }
}


