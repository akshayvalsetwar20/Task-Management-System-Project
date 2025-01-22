package com.tms.service;

import java.util.List;
import java.util.Optional;

import com.tms.dto.AttachmentDTO;

public interface AttachmentServiceImpl {
	
	AttachmentDTO addAttachment(AttachmentDTO attachmentDTO);

    List<AttachmentDTO> getAllAttachments();

    AttachmentDTO getAttachmentById(Integer attachmentId);

    AttachmentDTO updateAttachment(Integer attachmentId, AttachmentDTO attachmentDTO);

    Optional<AttachmentDTO> deleteAttachment(Integer attachmentId);

}
