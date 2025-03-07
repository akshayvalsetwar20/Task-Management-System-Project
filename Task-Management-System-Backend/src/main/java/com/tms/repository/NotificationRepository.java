package com.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer>{
	
	boolean existsById(Integer notificationId);
}
