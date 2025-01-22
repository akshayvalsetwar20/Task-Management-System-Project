package com.tms.controllertesting;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tms.controller.NotificationController;
import com.tms.dto.ApiResponse;
import com.tms.dto.NotificationDTO;
import com.tms.exception.NotificationAlreadyExistsException;
import com.tms.exception.NotificationNotFoundException;
import com.tms.service.NotificationService;
import com.tms.model.Notification;
import com.tms.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    private NotificationDTO notificationDTO;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        MockitoAnnotations.openMocks(this);

        // Sample NotificationDTO for tests
        notificationDTO = new NotificationDTO(1, "Test Notification", LocalDateTime.now(), 1);
    }

    // 1. Test for Adding Notification
    @Test
    void addNotificationTest() {
        // Arrange: Prepare Notification object for the test
        Notification notification = new Notification();
        notification.setNotificationId(1);
        notification.setText("Test Notification");
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUser(new User(1));  // Assuming User exists with ID 1

        // Mock the service method to return the Notification object wrapped in Optional
        when(notificationService.addNotification(any(), eq(notification.getNotificationId()), eq(notification.getText()), eq(notification.getCreatedAt())))
            .thenReturn(Optional.of(notification));  // Return the Notification as it's added successfully

        // Act: Call the controller's addNotification method with the Notification model
        ResponseEntity<Notification> response = notificationController.addNotification(notification);

        // Assert: Validate the response status and body (Notification object)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notification, response.getBody());

        // Verify: Ensure the service method was called exactly once with the correct arguments
        verify(notificationService, times(1)).addNotification(any(), eq(notification.getNotificationId()), eq(notification.getText()), eq(notification.getCreatedAt()));
    }


    // 2. Test for Updating Notification
    @Test
    void updateNotificationTest() {
        // Arrange: Prepare the Notification object for the test
        Notification notification = new Notification();
        notification.setNotificationId(1);
        notification.setText("Updated Notification Text");
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUser(new User(1));  // Assuming User exists with ID 1

        // Mock the service method to return the updated Notification object wrapped in Optional
        when(notificationService.updateNotification(eq(1), eq(notification))).thenReturn(Optional.of(notification));

        // Act: Call the controller's updateNotification method with the Notification model
        ResponseEntity<ApiResponse> response = notificationController.updateNotification(1, notification);

        // Assert: Validate the response status and message
        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("UPDATESUCCESS", response.getBody().getStatus());
        assertEquals("Notification updated successfully", response.getBody().getMessage());

        // Verify: Ensure the service method was called exactly once with the correct arguments
        verify(notificationService, times(1)).updateNotification(eq(1), eq(notification));
    }


    // 3. Test for Retrieving Notification by ID
    @Test
    void getNotificationByIdTest() {
        // Arrange: Mock the service method to return the DTO for a valid ID
        when(notificationService.getNotificationById(1)).thenReturn(Optional.of(notificationDTO));

        // Act: Call the controller's getNotificationById method
        ResponseEntity<?> response = notificationController.getNotificationById(1);

        // Assert: Validate the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notificationDTO, response.getBody());

        // Verify: Ensure service method was called exactly once
        verify(notificationService, times(1)).getNotificationById(1);
    }

    // 4. Test for Deleting Notification
    @Test
    void deleteNotificationTest() {
        // Arrange: Mock the service method to return the DTO for a valid deletion
        when(notificationService.deleteNotification(1)).thenReturn(Optional.of(notificationDTO));

        // Act: Call the controller's deleteNotification method
        ResponseEntity<ApiResponse> response = notificationController.deleteNotification(1);

        // Assert: Validate the response status and message
        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("DELETESUCCESS", response.getBody().getStatus());
        assertEquals("Notification deleted successfully", response.getBody().getMessage());

        // Verify: Ensure service method was called exactly once
        verify(notificationService, times(1)).deleteNotification(1);
    }

    // 5. Test for Deleting Notification when Not Found
    @Test
    void deleteNotificationNotFoundTest() {
        // Arrange: Mock the service method to return Optional.empty() (not found)
        when(notificationService.deleteNotification(1)).thenReturn(Optional.empty());

        // Act: Call the controller's deleteNotification method
        ResponseEntity<ApiResponse> response = notificationController.deleteNotification(1);

        // Assert: Validate the response status and message
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals("DLTFAILS", response.getBody().getStatus());
        assertEquals("Notification doesn't exist", response.getBody().getMessage());

        // Verify: Ensure service method was called exactly once
        verify(notificationService, times(1)).deleteNotification(1);
    }

}

