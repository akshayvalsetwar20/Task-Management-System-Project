package com.tms.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.tms.dto.NotificationDTO;
import com.tms.model.Notification;
import com.tms.model.User;
import com.tms.repository.NotificationRepository;

import jakarta.validation.Valid;


@Service  // This ensures that the validation annotations on the DTO are respected
public class NotificationService implements NotificationServiceImpl{

    @Autowired
    private NotificationRepository nr;

    // Convert Notification to NotificationDTO
    private NotificationDTO convertToDTO(Notification notification) {
        return new NotificationDTO(
            notification.getNotificationId(),
            notification.getText(),
            notification.getCreatedAt(),
            notification.getUser().getUserID()
        );
    }

    // Convert NotificationDTO to Notification
    private Notification convertToEntity(NotificationDTO dto) {
        User user = new User();
        user.setUserID(dto.getUserId()); // Assuming the User exists and has been set via the userId
        
        return new Notification(
            dto.getNotificationId(),
            dto.getText(),
            dto.getCreatedAt(),
            user
        );
    }

    // Get all notifications that are not deleted
    public List<NotificationDTO> getAllNotifications() {
        List<Notification> notifications = nr.findAll();
        return notifications.stream()
                .filter(notification -> !notification.isDeleted()) // Filtering logic stays in the service layer
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get notification by ID, only if it's not deleted
    public Optional<NotificationDTO> getNotificationById(Integer notificationId) {
        Optional<Notification> notification = nr.findById(notificationId);
        if (notification.isPresent() && !notification.get().isDeleted()) {
            return Optional.of(convertToDTO(notification.get()));
        }
        return Optional.empty();
    }

    // Update notification
    public Optional<NotificationDTO> updateNotification(Integer notificationId, @Valid NotificationDTO notificationDTO) {
        Optional<Notification> existingNotificationOpt = nr.findById(notificationId);
        if (existingNotificationOpt.isPresent() && !existingNotificationOpt.get().isDeleted()) {
            Notification existingNotification = existingNotificationOpt.get();
            existingNotification.setText(notificationDTO.getText());
            existingNotification.setCreatedAt(notificationDTO.getCreatedAt());
            existingNotification.setUser(new User(notificationDTO.getUserId()));
            return Optional.of(convertToDTO(nr.save(existingNotification))); // Save the updated notification and return DTO
        }
        return Optional.empty();
    }

    // Add new notification
    public Optional<NotificationDTO> addNotification(@Valid NotificationDTO notificationDTO) {
        if (nr.existsById(notificationDTO.getNotificationId())) {
            return Optional.empty(); // Notification already exists
        }

        Notification notification = convertToEntity(notificationDTO);
        return Optional.of(convertToDTO(nr.save(notification)));
    }

    // Soft delete notification by marking it as deleted
    public Optional<NotificationDTO> deleteNotification(Integer notificationId) {
        Optional<Notification> notificationOpt = nr.findById(notificationId);
        if (notificationOpt.isPresent() && !notificationOpt.get().isDeleted()) {
            Notification notification = notificationOpt.get();
            notification.setDeleted(true); // Mark the notification as deleted
            return Optional.of(convertToDTO(nr.save(notification))); // Save and return DTO
        }
        return Optional.empty();
    }
    
    public Optional<Notification> addNotification(User user, int notificationId, String text, LocalDateTime createdAt) {
        if (nr.existsById(notificationId)) {
            return Optional.empty(); // Notification already exists
        }

        Notification n = new Notification();
        n.setUser(user);
        n.setNotificationId(notificationId);
        n.setText(text);
        n.setCreatedAt(createdAt);
        return Optional.of(nr.save(n)); // Save new notification
    }
    
    public Optional<Notification> updateNotification(int notificationId, Notification notification) {
        Optional<Notification> existingNotificationOpt = nr.findById(notificationId);
        if (existingNotificationOpt.isPresent() && !existingNotificationOpt.get().isDeleted()) {
            Notification existingNotification = existingNotificationOpt.get();
            existingNotification.setText(notification.getText());
            existingNotification.setCreatedAt(notification.getCreatedAt());
            return Optional.of(nr.save(existingNotification)); // Save the updated notification
        }
        return Optional.empty(); // If not found or already deleted
    }
}
