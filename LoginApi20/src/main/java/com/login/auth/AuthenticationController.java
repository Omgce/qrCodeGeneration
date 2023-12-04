package com.login.auth;

import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login.api.jwt.AuthToken;
import com.login.api.jwt.JWTToken;
import com.login.api.jwt.QrToken;
import com.login.api.jwt.TokenProvider;
import com.login.api.user.dto.LoginDTO;
import com.login.api.user.dto.VerifyTokenRequestDTO;
import com.login.api.user.entity.User;
import com.login.api.user.repositories.UserRepository;
import com.login.api.user.service.OtpService;
import com.login.api.user.service.UserService;
import com.login.errorDto.ErrorDetails;
import com.login.qrController.LoginQRCode;
import com.login.qrController.LoginQRCodeRepository;
import com.login.qrController.QrStatusService;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	private LoginQRCodeRepository qrRepo;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OtpService otpService;

	@Autowired
	private UserService service;

	@Autowired
	private QrStatusService qrService;

	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> authorize(@Valid @RequestBody LoginDTO loginDTO) {

		Optional<User> exitToken = userRepository.findBySendLoginToken(loginDTO.getToken());
		String deviceIdRequest = loginDTO.getDeviceId();
		String deviceId = exitToken.get().getDeviceId();

		if (deviceIdRequest.equals(deviceId) && exitToken.isPresent()) {

			String email = exitToken.get().getEmail();
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
					loginDTO.getPin());
			try {
				Authentication authentication = this.authManager.authenticate(authenticationToken);
				String token = tokenProvider.createToken(authentication, loginDTO.isRememberMe());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				Integer existOtp = exitToken.get().getVerifyOtp();
				System.out.println("getVerifyOtp: " + existOtp);
				String otp = String.valueOf(existOtp);
				byte[] byteArray = otp.getBytes();
				String base64EncodedFirst = Base64.getEncoder().encodeToString(byteArray);
				byte[] byteArrayFirst = base64EncodedFirst.getBytes();
				String base64EncodedSecond = Base64.getEncoder().encodeToString(byteArrayFirst);
				String substring = base64EncodedSecond.substring(base64EncodedSecond.length() - 4);
				String substring2 = base64EncodedSecond.substring(0, base64EncodedSecond.length() - 4);
				String substring3 = substring + substring2;
				byte[] byteArraySecond = substring3.getBytes();
				String base64EncodedThird = Base64.getEncoder().encodeToString(byteArraySecond);
				service.setAuthToken(token, email);
				AuthToken response = new AuthToken(token, base64EncodedThird, new Date(), HttpStatus.OK.value(),
						"Authentication successful", true);

				return new ResponseEntity<>(response, HttpStatus.OK);

			} catch (AuthenticationException exception) {

				int customStatusCode = 1000;
				log.error("Your enter pin: " + loginDTO.getPin() + " is not vaild.");
				String errorMessage = "Please enter your correct pin.";

				ErrorDetails errorDetails = new ErrorDetails(new Date(), customStatusCode, "Bad Request", errorMessage);
				return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
			}
		} else {
			int customStatusCode = 1000;
			log.error("Your enter pin: " + loginDTO.getPin() + " is not vaild.");
			String errorMessage = "User not found.";
			ErrorDetails errorDetails = new ErrorDetails(new Date(), customStatusCode, "Bad Request", errorMessage);
			return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/verify")
	public ResponseEntity<?> verifyOtp(@Valid @RequestBody VerifyTokenRequestDTO verifyTokenRequest) {
		String otpToken = verifyTokenRequest.getToken();
		String enterOtp = verifyTokenRequest.getOtp();
		Integer validateOtp = Integer.valueOf(enterOtp);

		Boolean rememberMe = verifyTokenRequest.getRememberMe();

		Optional<User> user = userRepository.findByAuthToken(otpToken);

		if (user.isPresent()) {
			String email = user.get().getEmail();
			boolean isOtpValid = otpService.validateOTP(email, validateOtp);

			System.out.println(isOtpValid);

			if (!isOtpValid) {
				int customStatusCode = 1000;
				log.error("Your enter pin: " + enterOtp + " is not vaild.");
				String errorMessage = "Please enter your correct Otp.";
				ErrorDetails errorDetails = new ErrorDetails(new Date(), customStatusCode, "Bad Request", errorMessage);
				return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
			}
			String token = tokenProvider.createTokenAfterVerifiedOtp(email, rememberMe);
			JWTToken response = new JWTToken(token, new Date(), HttpStatus.OK.value(), "Authentication successful",
					true);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		int customStatusCode = 1000;
		log.error("Your enter pin: " + enterOtp + " is not vaild.");
		String errorMessage = "User not found.";
		ErrorDetails errorDetails = new ErrorDetails(new Date(), customStatusCode, "Bad Request", errorMessage);
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

	}

	@PostMapping(value = "/qrTokenVerification")
	public ResponseEntity<?> dataVerification(@Valid @RequestBody LoginQRCode loginQRCode) {

		String qrToken = loginQRCode.getQrToken();
		String deviceId = loginQRCode.getDeviceId();

		System.out.println("qrToken: " + qrToken);
		System.out.println("deviceId: " + deviceId);

		Optional<LoginQRCode> existqrToken = qrRepo.findLoginQRCodeByQrToken(qrToken);
		Optional<User> existDevice = userRepository.findByDeviceId(deviceId);
		if (existDevice.isPresent() && existqrToken.isPresent()) {
			boolean qrStatus = qrService.setStatus(qrToken);

			// System.out.println("setQrStatus: " + setQrStatus);
			if (!qrStatus) {
				int customStatusCode = 1000;
				log.error("Your enter token: " + qrToken + " is not vaild.");
				String errorMessage = "User not found 1.";
				ErrorDetails errorDetails = new ErrorDetails(new Date(), customStatusCode, "Bad Request", errorMessage);
				return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
			} else {

				QrToken response = new QrToken("User Verified Successfully", new Date(), HttpStatus.OK.value(),
						"User Verified Successfully.", true);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} else {
			int customStatusCode = 1000;
			log.error("Your enter token: " + qrToken + " is not vaild.");
			String errorMessage = "User not found 2.";
			ErrorDetails errorDetails = new ErrorDetails(new Date(), customStatusCode, "Bad Request", errorMessage);
			return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/qrVerification")
	public ResponseEntity<?> qrVerification(@Valid @RequestBody LoginQRCode loginQRCode) {
		return null;
	}

}
