package com.tms.controllertesting;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tms.controller.AttachmentController;
import com.tms.dto.ApiResponse;
import com.tms.dto.AttachmentDTO;
import com.tms.service.AttachmentService;
import com.tms.exception.AttachmentNotFoundException;

import java.util.Optional;

class AttachmentControllerTest {

    @Mock
    private AttachmentService attachmentService;

    @InjectMocks
    private AttachmentController attachmentController;

    private AttachmentDTO attachmentDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        attachmentDTO = new AttachmentDTO(1, "file.txt", "/path/to/file", 1);  // Sample DTO
    }

    // 1. Test for Adding Attachment
    @Test
    void addAttachmentTest() {
        // Mock the service method
        when(attachmentService.addAttachment(attachmentDTO)).thenReturn(attachmentDTO);

        // Call the controller method
        ResponseEntity<ApiResponse> response = attachmentController.addAttachment(attachmentDTO);

        // Validate response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals("POSTSUCCESS", response.getBody().getStatus());
        assertEquals("Attachment added successfully", response.getBody().getMessage());

        // Verify service method was called
        verify(attachmentService, times(1)).addAttachment(attachmentDTO);
    }

    // 2. Test for Updating Attachment
    @Test
    void updateAttachmentTest() {
        // Mock the service method
        when(attachmentService.updateAttachment(anyInt(), eq(attachmentDTO))).thenReturn(attachmentDTO);

        // Call the controller method
        ResponseEntity<ApiResponse> response = attachmentController.updateAttachment(1, attachmentDTO);

        // Validate response
        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("UPDATESUCCESS", response.getBody().getStatus());
        assertEquals("Attachment updated successfully", response.getBody().getMessage());

        // Verify service method was called
        verify(attachmentService, times(1)).updateAttachment(eq(1), eq(attachmentDTO));
    }

    // 3. Test for Retrieving Attachment by ID
    @Test
    void getAttachmentByIdTest() {
        // Mock the service method
        when(attachmentService.getAttachmentById(1)).thenReturn(attachmentDTO);

        // Call the controller method
        ResponseEntity<?> response = attachmentController.getAttachmentById(1);

        // Validate response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(attachmentDTO, response.getBody());

        // Verify service method was called
        verify(attachmentService, times(1)).getAttachmentById(1);
    }

    // 4. Test for Deleting Attachment
    @Test
    void deleteAttachmentTest() {
        // Mock the service method
        when(attachmentService.deleteAttachment(1)).thenReturn(Optional.of(attachmentDTO));

        // Call the controller method
        ResponseEntity<ApiResponse> response = attachmentController.deleteAttachment(1);

        // Validate response
        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("DELETESUCCESS", response.getBody().getStatus());
        assertEquals("Attachment marked as deleted successfully", response.getBody().getMessage());

        // Verify service method was called
        verify(attachmentService, times(1)).deleteAttachment(1);
    }

    // 5. Test for Deleting Attachment when Not Found
    @Test
    void deleteAttachmentNotFoundTest() {
        // Mock the service method to return empty (not found)
        when(attachmentService.deleteAttachment(1)).thenReturn(Optional.empty());

        // Call the controller method and expect an exception
        try {
            attachmentController.deleteAttachment(1);
            fail("Expected AttachmentNotFoundException to be thrown");
        } catch (AttachmentNotFoundException ex) {
            assertEquals("Attachment with ID 1 doesn't exist or is already deleted", ex.getMessage());
        }

        // Verify service method was called
        verify(attachmentService, times(1)).deleteAttachment(1);
    }
}
