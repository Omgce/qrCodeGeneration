package com.login.qrController;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class LoginQRCode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true, name = "qr_token")
	@NotNull
	@NotEmpty
	private String qrToken;

	private String deviceId;

	@Column(name = "qr_status")
	private Boolean qrStatus;

	public LoginQRCode() {
	}

	public LoginQRCode(@NotNull @NotEmpty String qrToken, String deviceId, Boolean qrStatus) {

		this.qrToken = qrToken;
		this.deviceId = deviceId;
		this.qrStatus = qrStatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQrToken() {
		return qrToken;
	}

	public void setQrToken(String qrToken) {
		this.qrToken = qrToken;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Boolean getQrStatus() {
		return qrStatus;
	}

	public void setQrStatus(Boolean qrStatus) {
		this.qrStatus = qrStatus;
	}

}
