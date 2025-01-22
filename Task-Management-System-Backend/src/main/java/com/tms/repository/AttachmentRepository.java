package com.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.model.Attachment;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Integer>{

}
