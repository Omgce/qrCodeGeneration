package com.login.errorDto;

import java.time.LocalDateTime;
import java.util.List;

public class CustomErrorResponse {
	private LocalDateTime timestamp;
	private int status;
	private String message;
	private List<String> errors;

	public CustomErrorResponse(LocalDateTime timestamp, int status, String message, List<String> errors) {

		this.timestamp = timestamp;
		this.status = status;
		this.message = message;
		this.errors = errors;
	}

	public CustomErrorResponse() {

	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}