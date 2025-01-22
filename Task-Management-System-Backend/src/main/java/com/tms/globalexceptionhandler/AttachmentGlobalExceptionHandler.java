package com.tms.globalexceptionhandler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tms.dto.ApiResponse;
import com.tms.exception.AttachmentAlreadyDeletedException;
import com.tms.exception.AttachmentNotFoundException;
import com.tms.exception.InvalidAttachmentDataException;

@ControllerAdvice
public class AttachmentGlobalExceptionHandler {
	
	 @ExceptionHandler(AttachmentNotFoundException.class)
	    public ResponseEntity<ApiResponse> handleAttachmentNotFoundException(AttachmentNotFoundException ex) {
	        ApiResponse response = new ApiResponse("DLTFAILS", ex.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	  }
	 
	 @ExceptionHandler(InvalidAttachmentDataException.class)
	    public ResponseEntity<ApiResponse> handleInvalidAttachmentDataException(InvalidAttachmentDataException ex) {
	        ApiResponse response = new ApiResponse("INVALIDATTACHMENTDATA", ex.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
	 
	 @ExceptionHandler(AttachmentAlreadyDeletedException.class)
	    public ResponseEntity<ApiResponse> handleAttachmentAlreadyDeletedException(AttachmentAlreadyDeletedException ex) {
	        // Create ApiResponse to send back to client
	        ApiResponse response = new ApiResponse("ATTACHMENTALREADYDELETED", ex.getMessage());
	        // Return the response with BAD_REQUEST status (or another appropriate status)
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
	 
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
	        // Get all validation errors
	        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
	                .map(fieldError -> fieldError.getDefaultMessage()) // Extract default messages
	                .collect(Collectors.toList());
	 
	        // Build response with error messages
	        ApiResponse apiResponse = new ApiResponse("VALIDATION_FAILED", errorMessages.get(0));
	        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	    }

}
