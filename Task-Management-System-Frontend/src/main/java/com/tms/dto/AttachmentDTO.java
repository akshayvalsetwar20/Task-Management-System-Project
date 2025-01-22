package com.tms.dto;

public class AttachmentDTO {

    private Integer attachmentID;
    
    private String fileName;

    private String filePath;
    
    private Integer taskID;

    // Constructor
    public AttachmentDTO(String fileName, String filePath, Integer taskID) {
//        this.attachmentID = attachmentID;
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
