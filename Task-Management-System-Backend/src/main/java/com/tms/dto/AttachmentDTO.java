package com.tms.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AttachmentDTO {

    private Integer attachmentID;

    @NotNull(message = "File name cannot be null.")
    @NotEmpty(message = "File name cannot be empty.")
    @Size(max = 255, message = "File name cannot exceed 255 characters.")
    private String fileName;

    @NotNull(message = "File path cannot be null.")
    @NotEmpty(message = "File path cannot be empty.")
    @Size(max = 500, message = "File path cannot exceed 500 characters.")
    private String filePath;
    
//    @NotNull(message = "TaskId cannot be null.")
    private Integer taskID;

    // Constructor
    public AttachmentDTO(Integer attachmentID, String fileName, String filePath, Integer taskID) {
        this.attachmentID = attachmentID;
        this.fileName = fileName;
        this.filePath = filePath;
        this.taskID = taskID;
    }

    public AttachmentDTO(
			String fileName,
			String filePath,
			Integer taskID) {
		super();
		this.fileName = fileName;
		this.filePath = filePath;
		this.taskID = taskID;
	}

	// Default Constructor
    public AttachmentDTO() {}

    // Getters and Setters
    public Integer getAttachmentID() {
        return attachmentID;
    }

    public void setAttachmentID(Integer attachmentID) {
        this.attachmentID = attachmentID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getTaskID() {
        return taskID;
    }

    public void setTaskID(Integer taskID) {
        this.taskID = taskID;
    }
}
