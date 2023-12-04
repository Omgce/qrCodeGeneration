package com.login.api.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.login.errorDto.ApiError;
import com.login.errorDto.ErrorDTO;
import com.login.errorDto.ErrorDetails;
import com.login.errorDto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorDTO handleConstraintViolationException(HttpServletRequest request, Exception ex) {
		ErrorDTO error = new ErrorDTO();

		error.setTimestamp(new Date());
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.addError(ex.getMessage());
		error.setPath(request.getServletPath());

		log.error(ex.getMessage(), ex);

		return error;

	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
		ex.printStackTrace();
		log.error("DataIntegrityViolationException: {} ", ex.getMessage());

		String errorMessage = "An error occurred while processing your request. Please try again later.";
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Internal Server Error", errorMessage);

		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
		ex.printStackTrace();
		log.error("You do not have permission to access this resource. ", ex.getMessage());

		String errorMessage = "You do not have permission to access this resource.";
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.FORBIDDEN.value(), "Forbidden",
				errorMessage);

		return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);

	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorDetails> handleBadCredentialsException(BadCredentialsException ex) {
		ex.printStackTrace();
		log.error("Invalid credentials. Please check your username and password. ", ex.getMessage());

		String errorMessage = "Invalid credentials. Please check your username and password.";
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.UNAUTHORIZED.value(), "Unauthorized",
				errorMessage);

		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);

	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorDetails> handleAuthenticationException(AuthenticationException ex) {
		ex.printStackTrace();

		log.error("UNAUTHORIZED: {} ", ex.getMessage());

		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.UNAUTHORIZED.value(), "Unauthorized",
				ex.getMessage());

		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, List<String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		log.error("MethodArgumentNotValidException: {} ", ex.getMessage());
		Map<String, List<String>> errorsMap = new HashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			String field = error.getField();
			String message = error.getDefaultMessage();
			errorsMap.computeIfAbsent(field, key -> new ArrayList<>()).add(message);
		}
		return ResponseEntity.badRequest().body(errorsMap);

	}

	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<ErrorResponse> handleInvalidRequestException(InvalidRequestException ex) {
		log.error("InvalidRequestException: {} ", ex.getMessage());
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), new Date());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiError> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		log.error("HttpMessageNotReadableException: {} ", ex.getMessage());
		ApiError apiError = new ApiError();
		apiError.setStatus(HttpStatus.BAD_REQUEST);
		apiError.setMessage("Invalid JSON request");
		apiError.setTimestamp(LocalDateTime.now());
		apiError.setError(ex.getMessage());

		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<ApiError> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {

		log.error("HttpMediaTypeNotSupportedException: {} ", ex.getMessage());
		ApiError apiError = new ApiError();
		apiError.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		apiError.setMessage("Unsupported Media Type");
		apiError.setTimestamp(LocalDateTime.now());
		apiError.setError(ex.getMessage());

		return new ResponseEntity<>(apiError, HttpStatus.UNSUPPORTED_MEDIA_TYPE);

	}

}
