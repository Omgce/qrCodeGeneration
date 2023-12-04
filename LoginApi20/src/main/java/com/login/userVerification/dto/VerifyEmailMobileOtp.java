package com.login.userVerification.dto;

public class VerifyEmailMobileOtp {

	private Integer emailOtp;

	private Integer mobileOtp;

	private String token;

	private Boolean rememberMe;

	public Integer getEmailOtp() {
		return emailOtp;
	}

	public void setEmailOtp(Integer emailOtp) {
		this.emailOtp = emailOtp;
	}

	public Integer getMobileOtp() {
		return mobileOtp;
	}

	public void setMobileOtp(Integer mobileOtp) {
		this.mobileOtp = mobileOtp;
	}

	public Boolean getRememberMe() {
		return rememberMe;
	}

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

