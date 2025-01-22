package com.tms.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;


public class NotificationDTO {

    private Integer notificationId;  // Changed from int to Integer
    private String text;
    private LocalDateTime createdAt;

    private Integer userId;  // Changed from int to Integer

    // Getters and Setters
    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // Constructor for creating DTO objects
    public NotificationDTO(Integer notificationId, String text, LocalDateTime createdAt, Integer userId) {
        this.notificationId = notificationId;
        this.text = text;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    // Default constructor
    public NotificationDTO() {}
}
