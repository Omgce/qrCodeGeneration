package com.login.userVerification.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class VerifyEmailOrMobile {

	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
	@Size(min = 1, max = 50)
	private String email;

	@Size(min = 4, max = 32)
	private String mobile;

	private Boolean rememberMe;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
	
	
	
}
