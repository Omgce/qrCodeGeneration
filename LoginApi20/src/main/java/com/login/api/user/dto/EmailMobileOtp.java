package com.login.api.user.dto;

/**
 * Login Data Transfer Object
 *
 * @class LoginDTO
 */
public class EmailMobileOtp {

	private Boolean rememberMe;

	public Boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

}
