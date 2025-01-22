package com.tms.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "notificationid")
    private int notificationId;  // Field name adjusted to follow camelCase convention
    
    @Column(name = "text")
    private String text;  // Field name adjusted to follow camelCase convention
    
    @Column(name = "createdat")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;  // Field name adjusted to follow camelCase convention
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userid", nullable = false)
    private User user;


    @JsonIgnore
    private boolean isDeleted = false;  // Default to false
    
    // Getters and Setters
    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    // Constructor to initialize all fields (without isDeleted)
    public Notification(int notificationId, String text, LocalDateTime createdAt, User user) {
        this.notificationId = notificationId;
        this.text = text;
        this.createdAt = createdAt;
        this.user = user;
    }

    // Default constructor
    public Notification() {}
}
