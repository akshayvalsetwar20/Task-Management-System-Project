package com.tms.service;
 
import com.tms.dto.CommentDTO;
import com.tms.exception.CommentAlreadyExistException;
import com.tms.exception.CommentNotFoundException;
import com.tms.model.Comment;
import com.tms.model.Task;
import com.tms.model.User;
import com.tms.repository.CommentRepository;
import com.tms.repository.TaskRepository;
import com.tms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.stream.Collectors;
 
@Service
public class CommentService implements CommentServiceImpl{
 
	@Autowired
	private CommentRepository commentRepository;
 
	@Autowired
	private TaskRepository taskRepository;
 
	@Autowired
	private UserRepository userRepository;
 
	// Create a new comment
	public void createComment(CommentDTO commentDTO) throws CommentAlreadyExistException {
		// Check if a comment with the same text already exists
		boolean commentExists = commentRepository.existsByTextAndIsDeletedFalse(commentDTO.getText());
		if (commentExists) {
			throw new CommentAlreadyExistException("Comment with this text already exists.");
		}
 
		// Convert DTO to entity
		Comment comment = new Comment();
		comment.setText(commentDTO.getText());
		comment.setCreatedAt(commentDTO.getCreatedAt());
 
		// Fetch Task and User from their respective repositories
		Task task = taskRepository.findById(commentDTO.getTaskId())
				.orElseThrow(() -> new RuntimeException("Task not found"));
		User user = userRepository.findById(commentDTO.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));
 
		comment.setTask(task);
		comment.setUser(user);
		comment.setDeleted(false); // New comment is not deleted
 
		// Save the comment
		commentRepository.save(comment);
	}
 
	// Fetch all comments that are not deleted
	public List<CommentDTO> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
 
        // If no comments are found, return an empty list
        if (comments.isEmpty()) {
            return List.of();
        }
 
        return comments.stream()
        		.filter(comment->!comment.isDeleted())
                .map(comment -> new CommentDTO(
                        comment.getCommentId(),
                        comment.getText(),
                        comment.getCreatedAt(),
                        comment.getTask().getTaskId(),
                        comment.getUser().getUserID()
                ))
                .collect(Collectors.toList());
    }
 
	// Fetch a comment by ID if it's not deleted
	public CommentDTO getCommentById(Integer commentId) throws CommentNotFoundException {
        Comment comment = commentRepository.findByCommentIdAndIsDeletedFalse(commentId);
        if (comment == null) {
            throw new CommentNotFoundException("Comment not found");
        }
 
        return new CommentDTO(
                comment.getCommentId(),
                comment.getText(),
                comment.getCreatedAt(),
                comment.getTask().getTaskId(),
                comment.getUser().getUserID()
        );
    }
 
	// Update an existing comment
	public void updateComment(Integer commentId, CommentDTO updatedCommentDTO)
			throws CommentNotFoundException, CommentAlreadyExistException {
		Comment comment = commentRepository.findByCommentIdAndIsDeletedFalse(commentId);
		if (comment == null) {
			throw new CommentNotFoundException("Comment not found for update");
		}
 
		// Check if a comment with the same text already exists
		boolean commentExists = commentRepository.existsByTextAndIsDeletedFalse(updatedCommentDTO.getText());
		if (commentExists) {
			throw new CommentAlreadyExistException("Comment with this text already exists.");
		}
 
		// Update comment
		comment.setText(updatedCommentDTO.getText());
		comment.setCreatedAt(updatedCommentDTO.getCreatedAt());
 
		commentRepository.save(comment);
	}
 
	// Soft delete a comment (mark as deleted)
	public void softDeleteComment(Integer commentId) throws CommentNotFoundException {
		Comment comment = commentRepository.findByCommentIdAndIsDeletedFalse(commentId);
		if (comment == null) {
			throw new CommentNotFoundException("Comment not found for deletion");
		}
 
		comment.setDeleted(true); // Mark as deleted
		commentRepository.save(comment);
	}
}
