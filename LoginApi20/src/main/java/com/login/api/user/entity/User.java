package com.login.api.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String username;

	@Column(name = "email", length = 50)
	@NotNull
	@Size(min = 4, max = 50)
	private String email;

	@Column(name = "password", length = 100)
	@NotNull
	@Size(min = 4, max = 100)
	private String password;

	@Column(name = "mobile", length = 20)
	@NotNull
	@Size(min = 10)
	private String mobile;

	@Column(name = "enabled")
	@NotNull
	private Boolean enabled;

	@Column(name = "account_non_locked")
	private boolean accountNonLocked;

	@Column(name = "failed_attempt")
	private int failedAttempt;

	@Column(name = "lock_time")
	private Date lockTime;

	@Column(name = "send_otp_token")
	private String sendOtpToken;

	@Column(name = "mobile_otp")
	private String motp;

	@Column(name = "email_otp")
	private String eotp;

	@Column(name = "send_password_token")
	private String sendPasswordToken;

	@Column(name = "send_pin_token")
	private String sendPinToken;

	private String pin;

	private Date timestamp;

	@Column(name = "device_id")
	private String deviceId;

	@Column(name = "device_name")
	private String deviceName;

	private String latitude;

	private String longitude;

	@Column(name = "ip_address")
	private String ipAddress;

	private String geolocation;

	@Column(name = "send_login_token")
	private String sendLoginToken;

	@Column(name = "auth_token")
	private String authToken;

	@Column(name = "verify_otp")
	private Integer verifyOtp;

	public User() {
		super();
	}

	public User(String username, @NotNull @Size(min = 4, max = 50) String email,
			@NotNull @Size(min = 4, max = 100) String password, @NotNull @Size(min = 10) String mobile,
			@NotNull Boolean enabled, boolean accountNonLocked, int failedAttempt, Date lockTime, String sendOtpToken,
			String motp, String eotp, String sendPasswordToken, String sendPinToken, String pin, Date timestamp,
			String deviceId, String deviceName, String latitude, String longitude, String ipAddress, String geolocation,
			String sendLoginToken, String authToken, Integer verifyOtp) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.mobile = mobile;
		this.enabled = enabled;
		this.accountNonLocked = accountNonLocked;
		this.failedAttempt = failedAttempt;
		this.lockTime = lockTime;
		this.sendOtpToken = sendOtpToken;
		this.motp = motp;
		this.eotp = eotp;
		this.sendPasswordToken = sendPasswordToken;
		this.sendPinToken = sendPinToken;
		this.pin = pin;
		this.timestamp = timestamp;
		this.deviceId = deviceId;
		this.deviceName = deviceName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.ipAddress = ipAddress;
		this.geolocation = geolocation;
		this.sendLoginToken = sendLoginToken;
		this.authToken = authToken;
		this.verifyOtp = verifyOtp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public int getFailedAttempt() {
		return failedAttempt;
	}

	public void setFailedAttempt(int failedAttempt) {
		this.failedAttempt = failedAttempt;
	}

	public Date getLockTime() {
		return lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	public String getSendOtpToken() {
		return sendOtpToken;
	}

	public void setSendOtpToken(String sendOtpToken) {
		this.sendOtpToken = sendOtpToken;
	}

	public String getMotp() {
		return motp;
	}

	public void setMotp(String motp) {
		this.motp = motp;
	}

	public String getEotp() {
		return eotp;
	}

	public void setEotp(String eotp) {
		this.eotp = eotp;
	}

	public String getSendPasswordToken() {
		return sendPasswordToken;
	}

	public void setSendPasswordToken(String sendPasswordToken) {
		this.sendPasswordToken = sendPasswordToken;
	}

	public String getSendPinToken() {
		return sendPinToken;
	}

	public void setSendPinToken(String sendPinToken) {
		this.sendPinToken = sendPinToken;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getGeolocation() {
		return geolocation;
	}

	public void setGeolocation(String geolocation) {
		this.geolocation = geolocation;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getVerifyOtp() {
		return verifyOtp;
	}

	public void setVerifyOtp(Integer verifyOtp) {
		this.verifyOtp = verifyOtp;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getSendLoginToken() {
		return sendLoginToken;
	}

	public void setSendLoginToken(String sendLoginToken) {
		this.sendLoginToken = sendLoginToken;
	}

}