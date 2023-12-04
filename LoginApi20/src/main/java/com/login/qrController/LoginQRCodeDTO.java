package com.login.qrController;

public class LoginQRCodeDTO {

	private boolean qrStatus;

	public LoginQRCodeDTO() {
	}

	public LoginQRCodeDTO(boolean qrStatus) {
		this.qrStatus = qrStatus;
	}

	public boolean isQrStatus() {
		return qrStatus;
	}

	public void setQrStatus(boolean qrStatus) {
		this.qrStatus = qrStatus;
	}

//	private String qrToken;
//
//	private String deviceId;
//
//	public LoginQRCodeDTO() {
//
//	}
//
//	public LoginQRCodeDTO(String qrToken, String deviceId) {
//
//		this.qrToken = qrToken;
//		this.deviceId = deviceId;
//	}
//
//	public String getQrToken() {
//		return qrToken;
//	}
//
//	public void setQrToken(String qrToken) {
//		this.qrToken = qrToken;
//	}
//
//	public String getDeviceId() {
//		return deviceId;
//	}
//
//	public void setDeviceId(String deviceId) {
//		this.deviceId = deviceId;
//	}

}