package com.login.errorDto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorDetails {
	private Date timestamp;
	private int status;
	private String error;
	private String message;

	public ErrorDetails(Date timestamp, int status, String error, String message) {

		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
	}

	public ErrorDetails() {

	}

	public String getTimestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(timestamp);
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
