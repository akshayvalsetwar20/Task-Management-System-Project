package com.tms.controller;
 
import com.tms.dto.ApiResponse;
import com.tms.dto.CommentDTO;
import com.tms.exception.CommentAlreadyExistException;
import com.tms.exception.CommentNotFoundException;
import com.tms.service.CommentService;
 
import jakarta.validation.Valid;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
 
 
import java.util.List;
 
@RestController
@RequestMapping("/api/comments")
public class CommentController {
 
    @Autowired
    private CommentService commentService;
 
    @PostMapping("/post")
    public ResponseEntity<ApiResponse> createComment(@Valid @RequestBody CommentDTO commentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(new ApiResponse("VALIDATIONFAILS", errorMessage), HttpStatus.BAD_REQUEST);
        }
 
        try {
            commentService.createComment(commentDTO);
            return ResponseEntity.ok(new ApiResponse("POSTSUCCESS", "Comment added successfully"));
        } catch (CommentAlreadyExistException ex) {
            return new ResponseEntity<>(new ApiResponse("ADDFAILS", ex.getMessage()), HttpStatus.CONFLICT);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiResponse("ADDFAILS", "Failed to add comment"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 
    @GetMapping("/all")
    public ResponseEntity<Object> getAllComments() {
        try {
            List<CommentDTO> comments = commentService.getAllComments();
 
            if (comments.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse("GETALLFAILS", "Comment list is empty"), HttpStatus.OK);
            }
 
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiResponse("GETALLFAILS", "Failed to retrieve comments"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 
    @GetMapping("/{commentId}")
    public ResponseEntity<Object> getCommentById(@PathVariable Integer commentId) {
        try {
            CommentDTO comment = commentService.getCommentById(commentId);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } catch (CommentNotFoundException ex) {
            return new ResponseEntity<>(new ApiResponse("GETFAILS", ex.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiResponse("GETFAILS", "Failed to retrieve comment"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 
    @PutMapping("/update/{commentId}")
    public ResponseEntity<ApiResponse> updateComment(@PathVariable Integer commentId, @Valid @RequestBody CommentDTO updatedCommentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(new ApiResponse("VALIDATIONFAILS", errorMessage), HttpStatus.BAD_REQUEST);
        }
 
        try {
            commentService.updateComment(commentId, updatedCommentDTO);
            return ResponseEntity.ok(new ApiResponse("UPDATESUCCESS", "Comment updated successfully"));
        } catch (CommentNotFoundException ex) {
            return new ResponseEntity<>(new ApiResponse("UPDATEFAILS", "Comment not found"), HttpStatus.NOT_FOUND);
        } catch (CommentAlreadyExistException ex) {
            return new ResponseEntity<>(new ApiResponse("UPDATEFAILS", ex.getMessage()), HttpStatus.CONFLICT);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiResponse("UPDATEFAILS", "Failed to update comment"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
        try {
            commentService.softDeleteComment(commentId);
            return ResponseEntity.ok(new ApiResponse("DELETESUCCESS", "Comment deleted successfully"));
        } catch (CommentNotFoundException ex) {
            return new ResponseEntity<>(new ApiResponse("DLTFAILS", "Comment not found for deletion"), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ApiResponse("DLTFAILS", "Failed to delete comment"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}