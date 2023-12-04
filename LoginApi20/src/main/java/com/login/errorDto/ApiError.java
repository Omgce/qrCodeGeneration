package com.login.errorDto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ApiError {
	private HttpStatus status;
	private String message;
	private LocalDateTime timestamp;
	private String error;

	public ApiError() {
		super();
	}

	public ApiError(HttpStatus status, String message, LocalDateTime timestamp, String error) {
		super();
		this.status = status;
		this.message = message;
		this.timestamp = timestamp;
		this.error = error;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}