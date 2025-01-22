package com.tms.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.tms.model.Attachment;
import com.tms.model.Task;
import com.tms.dto.AttachmentDTO;
import com.tms.repository.AttachmentRepository;
import com.tms.repository.TaskRepository;

@Service
@Validated
public class AttachmentService implements AttachmentServiceImpl{
    
    @Autowired
    AttachmentRepository ar;
    
    @Autowired
    TaskRepository tr;

    private AttachmentDTO convertToDTO(Attachment attachment) {
        return new AttachmentDTO(
            attachment.getAttachmentID(),
            attachment.getFileName(),
            attachment.getFilePath(),
            attachment.getTask() != null ? attachment.getTask().getTaskId() : 0
        );
    }

    private Attachment convertToEntity(AttachmentDTO attachmentDTO) {
        Attachment attachment = new Attachment();
        attachment.setFileName(attachmentDTO.getFileName());
        attachment.setFilePath(attachmentDTO.getFilePath());
        
        if (attachmentDTO.getTaskID() != 0) {
            Optional<Task> taskOpt = tr.findById(attachmentDTO.getTaskID());
            if (taskOpt.isPresent()) {
                attachment.setTask(taskOpt.get()); // Set the Task entity
            } else {
                throw new IllegalArgumentException("Task with ID " + attachmentDTO.getTaskID() + " does not exist.");
            }
        }
        return attachment;
    }

    public AttachmentDTO addAttachment(AttachmentDTO attachmentDTO) {
        Attachment attachment = convertToEntity(attachmentDTO);
        Attachment savedAttachment = ar.save(attachment);
        return convertToDTO(savedAttachment);
    }

    
    public List<AttachmentDTO> getAllAttachments() {
        List<Attachment> attachments = ar.findAll();
        
        
        return attachments.stream()
                .filter(att -> !att.isDeleted())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AttachmentDTO getAttachmentById(Integer attachmentId) {
        Attachment attachment = ar.findById(attachmentId).orElse(null);

        if (attachment != null && !attachment.isDeleted()) {
            return convertToDTO(attachment);
        }

        return null;
    }

    public AttachmentDTO updateAttachment(Integer attachmentId, AttachmentDTO attachmentDTO) {
        Attachment existingAttachment = ar.findById(attachmentId).orElse(null);

        if (existingAttachment == null || existingAttachment.isDeleted()) {
            return null;
        }

        existingAttachment.setFileName(attachmentDTO.getFileName());
        existingAttachment.setFilePath(attachmentDTO.getFilePath());

        Attachment updatedAttachment = ar.save(existingAttachment);
        return convertToDTO(updatedAttachment);
    }

    public Optional<AttachmentDTO> deleteAttachment(Integer attachmentId) {
        Optional<Attachment> attachmentOpt = ar.findById(attachmentId);

        if (attachmentOpt.isEmpty() || attachmentOpt.get().isDeleted()) {
            return Optional.empty();
        }

        Attachment attachment = attachmentOpt.get();
        attachment.setDeleted(true);
        ar.save(attachment);
        return Optional.of(convertToDTO(attachment)); 
    }
}
