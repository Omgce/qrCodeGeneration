package com.login.api.jwt;

import java.io.IOException;
import java.util.Date;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.login.api.user.entity.User;
import com.login.api.user.repositories.UserRepository;
import com.login.api.user.service.OtpService;

import net.bytebuddy.utility.RandomString;

@Component
public class UserVerificationTokenProvider {

	private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long tokenValidityInSeconds;

	@Value("${jwt.expiration}")
	private long tokenValidityInSecondsForRememberMe;

	@Autowired
	private OtpService otpService;

	@Autowired
	private UserRepository userRepository;

	public String createEmailMobileOtpToken(String email, String mobile, Boolean rememberMe) throws IOException {
		User user = userRepository.findByEmail(email).orElseThrow(() -> {
			String errorMessage = "User with Email " + email + " not found!";
			log.warn(errorMessage);
			return new EntityNotFoundException(errorMessage);
		});

		otpService.generateEmailOtp(user.getEmail());
		otpService.generateMobileOtp(user.getEmail(), user.getMobile());
		
		return generateMobileEmailOtpToken(user.getEmail(), user.getMobile(), rememberMe);

	}

	public String createTokenAfterVerifiedOtp(String email, Boolean rememberMe) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> {
			String errorMessage = "User with Email " + email + " not found!";
			log.warn(errorMessage);
			return new EntityNotFoundException(errorMessage);
		});

		return generatePasswordVerifyOtpToken(user.getEmail(), rememberMe);

	}

	public String createTokenAfterPasswordValidate(String email, Boolean rememberMe) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> {
			String errorMessage = "User with Email " + email + " not found!";
			log.warn(errorMessage);
			return new EntityNotFoundException(errorMessage);
		});

		return generatePinVerifyOtpToken(user.getEmail(), rememberMe);

	}

	public String createTokenAfterPinValidate(String email, Boolean rememberMe) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> {
			String errorMessage = "User with Email " + email + " not found!";
			log.warn(errorMessage);
			return new EntityNotFoundException(errorMessage);
		});

		return generateLoginToken(user.getEmail(), rememberMe);
		
	}

	private String generateMobileEmailOtpToken(String email, String mobile, Boolean rememberMe) {

		long now = new Date().getTime();
		Date validity;
		if (Boolean.TRUE.equals(rememberMe)) {
			validity = new Date(now + this.tokenValidityInSecondsForRememberMe * 1000);
		} else {
			validity = new Date(now + this.tokenValidityInSeconds * 1000);
		}

		String token = RandomString.make(30);
		return token;
	}

	private String generatePasswordVerifyOtpToken(String email, Boolean rememberMe) {
		long now = new Date().getTime();
		Date validity;
		if (Boolean.TRUE.equals(rememberMe)) {
			validity = new Date(now + this.tokenValidityInSecondsForRememberMe * 1000);
		} else {
			validity = new Date(now + this.tokenValidityInSeconds * 1000);
		}
		String token = RandomString.make(30);
		return token;
	}

	private String generatePinVerifyOtpToken(String email, Boolean rememberMe) {
		long now = new Date().getTime();
		Date validity;
		if (Boolean.TRUE.equals(rememberMe)) {
			validity = new Date(now + this.tokenValidityInSecondsForRememberMe * 1000);
		} else {
			validity = new Date(now + this.tokenValidityInSeconds * 1000);
		}
		String token = RandomString.make(30);
		return token;
	}

	private String generateLoginToken(String email, Boolean rememberMe) {
		String token = RandomString.make(30);
		return token;
	}

}
