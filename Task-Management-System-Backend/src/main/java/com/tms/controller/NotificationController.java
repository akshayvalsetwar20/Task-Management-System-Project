package com.tms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import com.tms.dto.ApiResponse;
import com.tms.dto.NotificationDTO;
import com.tms.exception.NotificationAlreadyExistsException;
import com.tms.exception.NotificationNotFoundException;
import com.tms.model.Notification;
import com.tms.service.NotificationService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
@Validated
public class NotificationController {

    @Autowired
    NotificationService notificationService;
    
    @GetMapping("/all")
    public List<NotificationDTO> getAllNotifications() {
        return notificationService.getAllNotifications();  
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable("id") Integer id) {
        Optional<NotificationDTO> notificationOpt = notificationService.getNotificationById(id);
        if (!notificationOpt.isPresent()) {
            throw new NotificationNotFoundException("Notification with ID " + id + " doesn't exist or is deleted");
        }
        return ResponseEntity.ok(notificationOpt.get());
    }

    
    @PostMapping("/add")
    public ResponseEntity<Notification> addNotification(@RequestBody Notification notification) {
        Optional<Notification> addedNotification = notificationService.addNotification(
            notification.getUser(),
            notification.getNotificationId(),
            notification.getText(),
            notification.getCreatedAt()
        );
        if (addedNotification.isEmpty()) {
            throw new NotificationAlreadyExistsException("Notification already exists with ID " + notification.getNotificationId());
        }
        return ResponseEntity.ok(addedNotification.get());
    }

    
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateNotification(@PathVariable("id") Integer id, @RequestBody Notification notification) {
        Optional<Notification> updatedNotificationOpt = notificationService.updateNotification(id, notification);
        
        if (updatedNotificationOpt.isEmpty()) {
            
            ApiResponse response = new ApiResponse("UPDTFAILS", "Notification doesn't exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

       
        ApiResponse response = new ApiResponse("UPDATESUCCESS", "Notification updated successfully");
        return ResponseEntity.ok(response);
    }

   
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteNotification(@PathVariable("id") Integer id) {
        Optional<NotificationDTO> notificationOpt = notificationService.deleteNotification(id);

        if (!notificationOpt.isPresent()) {
            ApiResponse response = new ApiResponse("DLTFAILS", "Notification doesn't exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApiResponse response = new ApiResponse("DELETESUCCESS", "Notification deleted successfully");
        return ResponseEntity.ok(response);
    }
}
