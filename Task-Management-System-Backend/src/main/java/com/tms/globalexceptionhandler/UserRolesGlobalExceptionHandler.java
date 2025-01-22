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
import com.tms.exception.UserAlreadyExistsException;
import com.tms.exception.UserNotFoundException;
import com.tms.exception.UserRoleAlreadyExistException;
import com.tms.exception.UserRoleDoesNotExist;
import com.tms.exception.UserRoleListEmptyException;

@ControllerAdvice
public class UserRolesGlobalExceptionHandler {
	
	@ExceptionHandler(UserRoleAlreadyExistException.class)
	public ResponseEntity<ApiResponse> handleUserRoleAlreadyExistException(UserRoleAlreadyExistException ex) {
		ApiResponse apiResponse = new ApiResponse("ADDFAILS", ex.getMessage());
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserRoleDoesNotExist.class)
	public ResponseEntity<ApiResponse> handleUserRoleDoesNotExist(UserRoleDoesNotExist ex) {
		String errorCode = "GETFAILS";

		if ("DLTFAILS".equals(ex.getAction())) {
			errorCode = "DLTFAILS";
		}

		ApiResponse apiResponse = new ApiResponse(errorCode, ex.getMessage());
		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserRoleListEmptyException.class)
	public ResponseEntity<ApiResponse> handleUserRoleListEmptyException(UserRoleListEmptyException ex) {
		ApiResponse apiResponse = new ApiResponse("ADDFAILS", ex.getMessage());
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> fieldError.getDefaultMessage()) 
				.collect(Collectors.toList());
		ApiResponse apiResponse = new ApiResponse("VALIDATION_FAILED", errorMessages.get(0));
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
}
