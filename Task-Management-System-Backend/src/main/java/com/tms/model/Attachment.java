package com.tms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="attachment")
public class Attachment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="attachmentid")
	private int attachmentID;
	@Column(name="filename", nullable=false)
	private String FileName;
	@Column(name="filepath")
	private String FilePath;
	public Attachment(String fileName, String filePath, Task task) {
		super();
		FileName = fileName;
		FilePath = filePath;
		this.task = task;
	}
	@ManyToOne
	@JoinColumn(name="taskid")
	private Task task;
	@JsonIgnore
	private boolean isDeleted=false;
	
	public Attachment() {
		
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getAttachmentID() {
		return attachmentID;
	}
	public void setAttachmentID(int attachmentID) {
		this.attachmentID = attachmentID;
	}
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getFilePath() {
		return FilePath;
	}
	public void setFilePath(String filePath) {
		FilePath = filePath;
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	

}
