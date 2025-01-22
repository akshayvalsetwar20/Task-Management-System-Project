package com.tms.service;

import java.util.List;

import com.tms.dto.CommentDTO;
import com.tms.exception.CommentAlreadyExistException;
import com.tms.exception.CommentNotFoundException;

public interface CommentServiceImpl {
	
	void createComment(CommentDTO commentDTO) throws CommentAlreadyExistException;

    List<CommentDTO> getAllComments();

    CommentDTO getCommentById(Integer commentId) throws CommentNotFoundException;

    void updateComment(Integer commentId, CommentDTO updatedCommentDTO) throws CommentNotFoundException, CommentAlreadyExistException;

    void softDeleteComment(Integer commentId) throws CommentNotFoundException;

}
