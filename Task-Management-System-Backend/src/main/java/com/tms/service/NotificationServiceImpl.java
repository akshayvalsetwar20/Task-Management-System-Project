package com.tms.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.tms.dto.NotificationDTO;
import com.tms.model.Notification;
import com.tms.model.User;

public interface NotificationServiceImpl {
	
	List<NotificationDTO> getAllNotifications();

    Optional<NotificationDTO> getNotificationById(Integer notificationId);

    Optional<NotificationDTO> updateNotification(Integer notificationId, NotificationDTO notificationDTO);

    Optional<NotificationDTO> addNotification(NotificationDTO notificationDTO);

    Optional<NotificationDTO> deleteNotification(Integer notificationId);

    Optional<Notification> addNotification(User user, int notificationId, String text, LocalDateTime createdAt);

    Optional<Notification> updateNotification(int notificationId, Notification notification);

}
