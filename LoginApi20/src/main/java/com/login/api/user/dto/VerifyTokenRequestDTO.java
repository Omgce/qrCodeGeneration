package com.login.api.user.dto;

import javax.validation.constraints.NotNull;

public class VerifyTokenRequestDTO {

	@NotNull
	private String otp;

	@NotNull
	private String token;

	private Boolean rememberMe;

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Boolean getRememberMe() {
		return rememberMe;
	}

//	public Integer getOtp() {
//		return otp;
//	}
//
//	public void setOtp(Integer otp) {
//		this.otp = otp;
//	}

	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
