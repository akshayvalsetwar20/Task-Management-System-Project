package com.tms.dto;
 
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PastOrPresent;
 
import java.time.LocalDateTime;
 
public class CommentDTO {
 
    private Integer commentId;
 
    @NotEmpty(message = "Text cannot be empty")
    @Size(min = 1, max = 500, message = "Text must be between 1 and 500 characters")
    private String text;
 
    @NotNull(message = "Creation time cannot be null")
    @PastOrPresent(message = "Creation time cannot be in the future")
    private LocalDateTime createdAt;
 
    @NotNull(message = "Task ID cannot be null")
    private Integer taskId;
 
    @NotNull(message = "User ID cannot be null")
    private Integer userId;
 
 
    // Default constructor
    public CommentDTO() {
    }
 
    // Constructor with all fields
    public CommentDTO(Integer commentId, String text, LocalDateTime createdAt, Integer taskId, Integer userId) {
        this.commentId = commentId;
        this.text = text;
        this.createdAt = createdAt;
        this.taskId = taskId;
        this.userId = userId;
    }
 
    // Getters and Setters
    public Integer getCommentId() {
        return commentId;
    }
 
    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
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
 
    public Integer getTaskId() {
        return taskId;
    }
 
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
 
    public Integer getUserId() {
        return userId;
    }
 
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
 
   
 
    @Override
    public String toString() {
        return "CommentDTO{" +
                "commentId=" + commentId +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", taskId=" + taskId +
                ", userId=" + userId +
                '}';
    }
}