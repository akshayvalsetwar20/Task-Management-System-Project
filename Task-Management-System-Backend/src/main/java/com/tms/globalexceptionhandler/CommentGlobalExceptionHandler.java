package com.tms.globalexceptionhandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tms.dto.ApiResponse;
import com.tms.exception.CommentAlreadyExistException;
import com.tms.exception.CommentDoesNotExistException;
import com.tms.exception.CommentListEmptyException;

@RestControllerAdvice
public class CommentGlobalExceptionHandler {

    
    @ExceptionHandler(CommentAlreadyExistException.class)
    public ResponseEntity<ApiResponse> handleCommentAlreadyExist(CommentAlreadyExistException ex) {
        ApiResponse response = new ApiResponse("ADDFAILS", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    
    @ExceptionHandler(CommentDoesNotExistException.class)
    public ResponseEntity<ApiResponse> handleCommentDoesNotExist(CommentDoesNotExistException ex) {
        ApiResponse response = new ApiResponse("GETFAILS", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

   
    @ExceptionHandler(CommentListEmptyException.class)
    public ResponseEntity<ApiResponse> handleCommentListException(CommentListEmptyException ex) {
        ApiResponse response = new ApiResponse("GETALLFAILS", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
}

