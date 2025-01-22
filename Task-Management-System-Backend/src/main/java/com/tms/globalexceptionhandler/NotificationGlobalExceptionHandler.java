package com.tms.globalexceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tms.dto.ApiResponse;
import com.tms.exception.NotificationAlreadyExistsException;
import com.tms.exception.NotificationNotFoundException;

@ControllerAdvice
public class NotificationGlobalExceptionHandler {
	
	@ExceptionHandler(NotificationAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleNotificationAlreadyExistsException(NotificationAlreadyExistsException ex) {
        ApiResponse response = new ApiResponse("NOTIFICATIONEXISTS", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotificationNotFoundException(NotificationNotFoundException ex) {
        ApiResponse response = new ApiResponse("NOTIFICATIONNOTFOUND", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


}
