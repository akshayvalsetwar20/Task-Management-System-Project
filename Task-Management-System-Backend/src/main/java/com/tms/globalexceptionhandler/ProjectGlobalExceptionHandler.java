package com.tms.globalexceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tms.dto.ApiResponse;
import com.tms.exception.HighPriorityTaskEmptyException;

@ControllerAdvice
public class ProjectGlobalExceptionHandler {
	@ExceptionHandler(HighPriorityTaskEmptyException.class)
	public ResponseEntity<Object> handleHighPriorityTaskEmptyException(HighPriorityTaskEmptyException ex) {
		return new ResponseEntity<>(new ApiResponse("GETALLFAILS", ex.getMessage()), HttpStatus.NOT_FOUND);
	}

}
