package com.tms.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tms.dto.ApiResponse;
import com.tms.dto.AttachmentDTO;
import com.tms.exception.AttachmentNotFoundException;
import com.tms.exception.InvalidAttachmentDataException;
import com.tms.service.AttachmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {
    
    @Autowired
    private AttachmentService attachmentService;
    
    @PostMapping("/post")
    public ResponseEntity<ApiResponse> addAttachment(@Valid @RequestBody AttachmentDTO attachmentDTO) {
        if (attachmentDTO.getFileName() == null || attachmentDTO.getFileName().isEmpty()) {
            throw new InvalidAttachmentDataException("Filename cannot be null or empty.");
        }

        if (attachmentDTO.getFilePath() == null || attachmentDTO.getFilePath().isEmpty()) {
            throw new InvalidAttachmentDataException("Filepath cannot be null or empty.");
        }

        AttachmentDTO addedOrNot = attachmentService.addAttachment(attachmentDTO);

        if (addedOrNot != null) {
            ApiResponse res = new ApiResponse("POSTSUCCESS", "Attachment added successfully");
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } else {
            ApiResponse response = new ApiResponse("ADDFAILS", "Attachment already exists");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAttachments() {
        List<AttachmentDTO> attachments = attachmentService.getAllAttachments();

        if (attachments.isEmpty()) {
            ApiResponse response = new ApiResponse("GETALLFAILS", "Attachment list is empty");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(attachments);
    }

    // Get attachment by ID - only if it is not deleted
    @GetMapping("/{attachmentId}")
    public ResponseEntity<?> getAttachmentById(@PathVariable("attachmentId") int attachmentId) {
        AttachmentDTO attachment = attachmentService.getAttachmentById(attachmentId);
        if (attachment == null) {
            ApiResponse response = new ApiResponse("GETFAILS", "Attachment doesn't exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(attachment);
    }

    @PutMapping("/update/{attachmentId}")
    public ResponseEntity<ApiResponse> updateAttachment(@PathVariable("attachmentId") Integer attachmentId,
                                                         @Valid @RequestBody AttachmentDTO attachmentDTO) {
        AttachmentDTO updatedAttachment = attachmentService.updateAttachment(attachmentId, attachmentDTO);

        if (updatedAttachment == null) {
            ApiResponse response = new ApiResponse("UPDTFAILS", "Attachment doesn't exist or is deleted");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApiResponse response = new ApiResponse("UPDATESUCCESS", "Attachment updated successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{attachmentId}")
    public ResponseEntity<ApiResponse> deleteAttachment(@PathVariable("attachmentId") Integer attachmentId) {
        Optional<AttachmentDTO> attachmentOpt = attachmentService.deleteAttachment(attachmentId);

        if (attachmentOpt.isEmpty()) {
            throw new AttachmentNotFoundException("Attachment with ID " + attachmentId + " doesn't exist or is already deleted");
        }

        ApiResponse response = new ApiResponse("DELETESUCCESS", "Attachment marked as deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
