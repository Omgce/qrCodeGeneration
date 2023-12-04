package com.login.api.user.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.login.api.user.entity.User;
import com.login.api.user.repositories.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> findAllUsers() {
		return this.userRepository.findAll();
	}

	public String findEmailByUsername(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			return user.get().getEmail();
		}
		return null;
	}

	public static final int MAX_FAILED_ATTEMPTS = 3;

	private static final long LOCK_TIME_DURATION = 60 * 1000; // 1 minutes

	public Optional<User> getUserByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		return user;
	}

	public void increaseFailedAttempts(User user) {
		int newFailAttempts = user.getFailedAttempt() + 1;
		userRepository.updateFailedAttempts(newFailAttempts, user.getEmail());
	}

	public void resetFailedAttempts(String email) {
		userRepository.updateFailedAttempts(0, email);
	}

	public void lock(User user) {
		user.setAccountNonLocked(false);
		user.setLockTime(new Date());
		userRepository.save(user);
	}

	public boolean unlockWhenTimeExpired(User user) {
		long lockTimeInMillis = user.getLockTime().getTime();
		long currentTimeInMillis = System.currentTimeMillis();
		if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
			user.setAccountNonLocked(true);
			user.setLockTime(null);
			user.setFailedAttempt(0);
			userRepository.save(user);
			return true;
		}
		return false;
	}

	public void setEmailMobileOtpToken(String token, @Valid String email) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			user.get().setSendOtpToken(token);
		} else {
			throw new UsernameNotFoundException("Could not find any user with the email " + email);
		}
	}

	public void setPasswordToken(String token, @Valid String email) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			user.get().setSendPasswordToken(token);
		} else {
			throw new UsernameNotFoundException("Could not find any user with the email " + email);
		}
	}

	public boolean validatePassword(String email, String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for (byte b : bytes) {
				sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
			}
			password = sb.toString();
			Optional<User> user = userRepository.findByEmail(email);
			if (user.get().getPassword().equals(password)) {
				return true;
			}
			return false;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public void setPinToken(String token, @Valid String email) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			//user.get().setSendPasswordToken(token);
			user.get().setSendPinToken(token);
		} else {
			throw new UsernameNotFoundException("Could not find any user with the email " + email);
		}
	}

	public boolean setPin(String email, String pin, String deviceId, String deviceName, String latitude,
			String longitude, String ipAddress, String geolocation) {

		PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String encode = bCryptPasswordEncoder.encode(pin);

		Optional<User> user = userRepository.findByEmail(email);

		if (user.isPresent()) {
			user.get().setPin(encode);
			user.get().setTimestamp(new Date());
			user.get().setEnabled(true);
			user.get().setDeviceId(deviceId);
			user.get().setDeviceName(deviceName);
			user.get().setLongitude(longitude);
			user.get().setLatitude(latitude);
			user.get().setIpAddress(ipAddress);
			user.get().setGeolocation(geolocation);

			return true;
		}
		return false;
	}

	public void setFinalAuthenticationToken(String token, String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			//user.get().setSendPinToken(token);
			user.get().setSendLoginToken(token);
		} else {
			throw new UsernameNotFoundException("Could not find any user with the email " + email);
		}
	}
	
	public void setAuthToken(String token, String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			user.get().setAuthToken(token);
		} else {
			throw new UsernameNotFoundException("Could not find any user with the email " + email);
		}
	}

	public boolean setOtp(String email, Integer otp) {
		Optional<User> user = userRepository.findByEmail(email);
		System.out.println("otp: "+otp);
		user.get().setVerifyOtp(otp);
		return true;
	}

}
