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
import com.tms.exception.CategoryAlreadyExistException;
import com.tms.exception.CategoryDoesNotExistException;
import com.tms.exception.CategoryListEmptyException;

@RestControllerAdvice
public class CategoryGlobalExceptionHandler {

    
    @ExceptionHandler(CategoryAlreadyExistException.class)
    public ResponseEntity<ApiResponse> handleCategoryAlreadyExist(CategoryAlreadyExistException ex) {
        ApiResponse response = new ApiResponse("ADDFAILS", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    
    @ExceptionHandler(CategoryDoesNotExistException.class)
    public ResponseEntity<ApiResponse> handleCategoryDoesNotExist(CategoryDoesNotExistException ex) {
        ApiResponse response = new ApiResponse("GETFAILS", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    
    @ExceptionHandler(CategoryListEmptyException.class)
    public ResponseEntity<ApiResponse> handleCategoryListException(CategoryListEmptyException ex) {
        ApiResponse response = new ApiResponse("GETALLFAILS", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGlobalException(Exception ex) {
        // Returning a generic error response with code "INTERNAL_SERVER_ERROR"
        ApiResponse response = new ApiResponse("INTERNAL_SERVER_ERROR", "An unexpected error occurred: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
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

