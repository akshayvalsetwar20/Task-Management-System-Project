package com.tms.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


public class NotificationDTO {

    @Positive(message = "Notification ID must be a positive number.")
    private Integer notificationId;  // Changed from int to Integer

    @NotNull(message = "Text cannot be null.")
    @Size(min = 1, max = 255, message = "Text must be between 1 and 255 characters.")
    private String text;

    @NotNull(message = "Created date cannot be null.")
    @PastOrPresent(message = "Created date must be in the past or present.")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
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
