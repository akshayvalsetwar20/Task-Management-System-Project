package com.tms.globalexceptionhandler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tms.dto.ApiResponse;
import com.tms.exception.AttachmentNotFoundException;
import com.tms.exception.UserAlreadyExistsException;
import com.tms.exception.UserListEmptyException;
import com.tms.exception.UserNotFoundException;

@ControllerAdvice
public class UserGlobalExceptionHandler {
	
	@ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ApiResponse apiResponse = new ApiResponse("ADDFAILS", ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
	
    @ExceptionHandler(UserListEmptyException.class)
    public ResponseEntity<ApiResponse> handleUserListEmptyException(UserListEmptyException ex) {
        ApiResponse apiResponse = new ApiResponse("GETFAILS", ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFoundException(UserNotFoundException ex) {
    	String errorCode = "GETFAILS";
    	
        if ("UPDTFAILS".equals(ex.getAction())) {
            errorCode = "UPDTFAILS";
        }
        
        if ("DLTFAILS".equals(ex.getAction())) {
            errorCode = "DLTFAILS";
        }

        ApiResponse apiResponse = new ApiResponse(errorCode, ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);

    
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getDefaultMessage()) // Extract default messages
                .collect(Collectors.toList());

        ApiResponse apiResponse = new ApiResponse("VALIDATION_FAILED", errorMessages.get(0));
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

}
