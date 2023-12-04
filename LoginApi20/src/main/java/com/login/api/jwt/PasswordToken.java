package com.login.api.jwt;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PasswordToken {

	private String idToken;
	private Date timestamp;
	private int statusCode;
	private String message;
	private boolean status;

	public PasswordToken(String idToken, Date timestamp, int statusCode, String message, boolean status) {
		this.idToken = idToken;
		this.timestamp = timestamp;
		this.statusCode = statusCode;
		this.message = message;
		this.status = status;
	}

//	public JWTToken(String idToken) {
//		this.idToken = idToken;
//	}

	@JsonProperty("id_token")
	public String getIdToken() {
		return idToken;
	}

	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}

	public String getTimestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(timestamp);
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
