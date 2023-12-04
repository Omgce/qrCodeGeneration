package com.login.userVerification;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login.api.jwt.OtpToken;
import com.login.api.jwt.PasswordToken;
import com.login.api.jwt.PinToken;
import com.login.api.jwt.UserVerificationTokenProvider;
import com.login.api.user.entity.User;
import com.login.api.user.repositories.UserRepository;
import com.login.api.user.service.OtpService;
import com.login.api.user.service.UserService;
import com.login.errorDto.ErrorDetails;
import com.login.userVerification.dto.PinCreationDTO;
import com.login.userVerification.dto.VerifyEmailMobileOtp;
import com.login.userVerification.dto.VerifyEmailOrMobile;
import com.login.userVerification.dto.VerifyPasswordDTO;

@RestController
@RequestMapping("/userVerification")
public class UserVerificationController {

	private final Logger log = LoggerFactory.getLogger(UserVerificationController.class);

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserVerificationTokenProvider tokenProvider;

	@Autowired
	private OtpService otpService;

	@Autowired
	private UserService service;

	@PostMapping(value = "/emailMobileVerify")
	public ResponseEntity<?> emailMobileVerification(@Valid @RequestBody VerifyEmailOrMobile verifyEmailOrMobile)
			throws IOException {

		log.debug("Credentials: {}", verifyEmailOrMobile);

		String email = verifyEmailOrMobile.getEmail();
		String mobile = verifyEmailOrMobile.getMobile();

		// EmailMobileOtp emailMobileOtp = new EmailMobileOtp();
		Optional<User> userEmail = userRepo.findByEmail(verifyEmailOrMobile.getEmail());
		Optional<User> userMobile = userRepo.findByMobile(verifyEmailOrMobile.getMobile());

		if (userEmail.isPresent() && userMobile.isPresent()) {
			Boolean enabled = userEmail.get().getEnabled();
			if (enabled.equals(true)) {
				String token = tokenProvider.createEmailMobileOtpToken(email, mobile,
						verifyEmailOrMobile.isRememberMe());
				service.setEmailMobileOtpToken(token, email);
				OtpToken response = new OtpToken(token, new Date(), HttpStatus.OK.value(), "Verify the sent otp.",
						true);
				return new ResponseEntity<>(response, HttpStatus.OK);

			} else {
				int customStatusCode = 1000;
				log.error("Your email: " + email + " or Mobile: " + mobile + " is already registred");
				String errorMessage = "User divice is already registred , Please contact support.";
				ErrorDetails errorDetails = new ErrorDetails(new Date(), customStatusCode, "Bad Request", errorMessage);
				return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
			}
		} else {
			int customStatusCode = 1000;
			log.error("Your email: " + email + " or Mobile: " + mobile + " is not found");
			String errorMessage = "User not found.";
			ErrorDetails errorDetails = new ErrorDetails(new Date(), customStatusCode, "Bad Request", errorMessage);
			return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/verifyOtp")
	public ResponseEntity<?> verifyOtp(@Valid @RequestBody VerifyEmailMobileOtp verifyEmailMobileOtp) {

		String token = verifyEmailMobileOtp.getToken();
		Integer emailOtp = verifyEmailMobileOtp.getEmailOtp();
		Integer mobileOtp = verifyEmailMobileOtp.getMobileOtp();
		Boolean rememberMe = verifyEmailMobileOtp.getRememberMe();

		Optional<User> existToken = userRepo.findBySendOtpToken(token);
		if (existToken.isPresent()) {
			String email = existToken.get().getEmail();
			boolean isOtpValid = otpService.validateEmailMobileOTP(email, emailOtp, mobileOtp);

			if (!isOtpValid) {
				int customStatusCode = 1000;
				log.error("Your Email Otp: " + emailOtp + " and Mobile Otp: " + mobileOtp + " is not found.");
				String errorMessage = "Your Email Otp: " + emailOtp + " and Mobile Otp: " + mobileOtp
						+ " is not found.";
				ErrorDetails errorDetails = new ErrorDetails(new Date(), customStatusCode, "Bad Request", errorMessage);
				return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
			}
			String send_otp_token = tokenProvider.createTokenAfterVerifiedOtp(email, rememberMe);
			service.setPasswordToken(send_otp_token, email);
			PasswordToken response = new PasswordToken(send_otp_token, new Date(), HttpStatus.OK.value(),
					"Authentication successful", true);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			int customStatusCode = 1000;
			log.error("Your Email Otp: " + emailOtp + " and Mobile Otp: " + mobileOtp + " is not found.");
			String errorMessage = "Your Email Otp: " + emailOtp + "and Mobile Otp: " + mobileOtp + "is not found.";
			ErrorDetails errorDetails = new ErrorDetails(new Date(), customStatusCode, "Bad Request", errorMessage);
			return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping(value = "/verifyPassword")
	public ResponseEntity<?> verifyPassword(@Valid @RequestBody VerifyPasswordDTO verifyPasswordDTO) {

		String token = verifyPasswordDTO.getToken();
		String password = verifyPasswordDTO.getPassword();
		Boolean rememberMe = verifyPasswordDTO.getRememberMe();

		Optional<User> existToken = userRepo.findBySendPasswordToken(token);
		if (existToken.isPresent()) {
			String email = existToken.get().getEmail();
			boolean isOtpValid = service.validatePassword(email, password);
			if (!isOtpValid) {
				int customStatusCode = 1000;
				log.error("Your password: " + password + " is not found.");
				String errorMessage = "Your password: " + password + " is not found.";
				ErrorDetails errorDetails = new ErrorDetails(new Date(), customStatusCode, "Bad Request", errorMessage);
				return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
			}
			String send_pin_token = tokenProvider.createTokenAfterPasswordValidate(email, rememberMe);
			service.setPinToken(send_pin_token, email);
			OtpToken response = new OtpToken(send_pin_token, new Date(), HttpStatus.OK.value(),
					"Your password have verified.", true);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			int customStatusCode = 1000;
			log.error("Your password: " + password + " is not found.");
			String errorMessage = "User not found.";
			ErrorDetails errorDetails = new ErrorDetails(new Date(), customStatusCode, "Bad Request", errorMessage);
			return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping(value = "/verifyPin")
	public ResponseEntity<?> verifyPin(@Valid @RequestBody PinCreationDTO pinCreationDTO) {

		String token = pinCreationDTO.getToken();
		String pin = pinCreationDTO.getPin();
		String deviceId = pinCreationDTO.getDeviceId();
		String deviceName = pinCreationDTO.getDeviceName();
		String latitude = pinCreationDTO.getLatitude();
		String longitude = pinCreationDTO.getLongitude();
		String ipAddress = pinCreationDTO.getIpAddress();
		String geolocation = pinCreationDTO.getGeolocation();
		Boolean rememberMe = pinCreationDTO.getRememberMe();

		Optional<User> existToken = userRepo.findBySendPinToken(token);
		if (existToken.isPresent()) {
			String email = existToken.get().getEmail();
			boolean isOtpValid = service.setPin(email, pin, deviceId, deviceName, latitude, longitude, ipAddress,
					geolocation);
			if (!isOtpValid) {
				int customStatusCode = 1000;
				log.error("Your enter pin: " + pin + " is not vaild.");
				String errorMessage = "Your enter pin: " + pin + " is not vaild.";
				ErrorDetails errorDetails = new ErrorDetails(new Date(), customStatusCode, "Bad Request", errorMessage);
				return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
			}
			String send_auth_token = tokenProvider.createTokenAfterPinValidate(email, rememberMe);
			service.setFinalAuthenticationToken(send_auth_token, email);

			Optional<User> user = userRepo.findByEmail(email);
			String exitDeviceId = user.get().getDeviceId();
			PinToken response = new PinToken(send_auth_token, new Date(), HttpStatus.OK.value(),
					"Pin genrated successfully.", exitDeviceId, true);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			int customStatusCode = 1000;
			log.error("Your enter pin: " + pin + " is not vaild.");
			// String errorMessage = "Your enter pin: " + pin + " is not vaild.";
			String errorMessage = "User not found.";
			ErrorDetails errorDetails = new ErrorDetails(new Date(), customStatusCode, "Bad Request", errorMessage);
			return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		}
	}

}
