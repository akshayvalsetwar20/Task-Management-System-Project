package com.tms.dto;



import java.time.LocalDateTime;

public class CommentDTO {

    private Integer commentId;
    private String text;
    private LocalDateTime createdAt;
    private Integer taskId;
    private Integer userId;

    // Default constructor
    public CommentDTO() {}

    // Constructor for response (fetching data)
    public CommentDTO(Integer commentId, String text, LocalDateTime createdAt, Integer taskId, Integer userId) {
        this.commentId = commentId;
        this.text = text;
        this.createdAt = createdAt;
        this.taskId = taskId;
        this.userId = userId;
    }

    // Constructor for request (creating/updating data)
    public CommentDTO(String text, LocalDateTime createdAt, Integer taskId, Integer userId) {
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
