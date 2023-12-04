package com.login.api.user.dto;

import javax.validation.constraints.NotNull;

/**
 * Login Data Transfer Object
 *
 * @class LoginDTO
 */
public class LoginDTO {

	@NotNull
	private String pin;

	// private String token;
	@NotNull
	private String deviceId;

	@NotNull
	private String token;

	private Boolean rememberMe;

	public Boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
