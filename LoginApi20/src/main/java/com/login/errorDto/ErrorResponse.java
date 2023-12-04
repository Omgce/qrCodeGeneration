package com.login.errorDto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorResponse {

	private int status;
	private String message;
	private Date timestamp;

	public ErrorResponse(int status, String message, Date timestamp) {
		this.status = status;
		this.message = message;
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

	public String getTimestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(timestamp);
	}

	public void setTimestamp(Date timestamp) {

		this.timestamp = timestamp;
	}

}
