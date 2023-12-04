package com.login.api.user.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import com.login.api.user.dto.EmailDTO;
import com.login.api.user.entity.User;
import com.login.api.user.repositories.UserRepository;

@Description(value = "Service responsible for handling OTP related functionality.")
@Service
public class OtpService {

	private final Logger LOGGER = LoggerFactory.getLogger(OtpService.class);

	private OtpGenerator otpGenerator;
	private EmailOtpGenerator emailOtpGenerator;
	private MobileOtpGenerator mobileOtpGenerator;
	private EmailService emailService;

	@Autowired
	private MobileService mobileService;
	private UserService userService;

	@Autowired
	private UserRepository userRepo;

	public OtpService(OtpGenerator otpGenerator, EmailOtpGenerator emailOtpGenerator,
			MobileOtpGenerator mobileOtpGenerator, EmailService emailService, MobileService mobileService,
			UserService userService, UserRepository userRepo) {

		this.otpGenerator = otpGenerator;
		this.emailOtpGenerator = emailOtpGenerator;
		this.mobileOtpGenerator = mobileOtpGenerator;
		this.emailService = emailService;
		this.mobileService = mobileService;
		this.userService = userService;
		this.userRepo = userRepo;
	}

	public Boolean generateOtp(String key) {
		// generate otp
		Integer otpValue = otpGenerator.generateOTP(key);

		if (otpValue == -1) {
			LOGGER.error("OTP generator is not working...");
			return false;
		}

		LOGGER.info("Generated OTP: {}", otpValue);

		// fetch user e-mail from database
		String userEmail = userService.findEmailByUsername(key);
//		List<String> recipients = new ArrayList<>();
//		recipients.add(userEmail);
//
//		// generate emailDTO object
//		EmailDTO emailDTO = new EmailDTO();
//		emailDTO.setSubject("Spring Boot OTP Password.");
//		emailDTO.setBody("OTP Password: " + otpValue);
//		emailDTO.setRecipients(recipients);

		// send generated e-mail
//		return emailService.sendSimpleMessage(emailDTO);

		System.out.println("setOtp: " + otpValue);
		userService.setOtp(userEmail, otpValue);

		return true;
	}

	public Boolean generateEmailOtp(String key) {
		// generate otp
		Integer otpValue = emailOtpGenerator.generateEmailOtp(key);
		if (otpValue == -1) {
			LOGGER.error("OTP generator is not working...");
			return false;
		}

		LOGGER.info("Generated Email OTP: {}", otpValue);

		// fetch user e-mail from database
		String userEmail = userService.findEmailByUsername(key);

		Optional<User> user = userRepo.findByEmail(userEmail);
		String eotp = String.valueOf(otpValue);
		user.get().setEotp(eotp);

		List<String> recipients = new ArrayList<>();
		recipients.add(userEmail);

		// generate emailDTO object
		EmailDTO emailDTO = new EmailDTO();
		emailDTO.setSubject("nifipayments");
		emailDTO.setBody("OTP Password: " + otpValue);
		emailDTO.setRecipients(recipients);

		// send generated e-mail
		return emailService.sendSimpleMessage(emailDTO);
	}

	public Boolean generateMobileOtp(String key, String mobile) throws IOException {
		// generate otp
		Integer otpValue = mobileOtpGenerator.generateMobileOtp(key);
		if (otpValue == -1) {
			LOGGER.error("OTP generator is not working...");
			return false;
		}

		LOGGER.info("Generated Mobile OTP: {}", otpValue);

		String mobileOtp = Integer.toString(otpValue);

		Optional<User> user = userRepo.findByMobile(mobile);
		user.get().setMotp(mobileOtp);

		return mobileService.genrateOTPAndSendOnMobile(mobile, mobileOtp);

	}

	public Boolean validateEmailMobileOTP(String key, Integer emailOtp, Integer mobileOtp) {

		System.out.println("emailOtp: "+emailOtp);
		System.out.println("mobileOtp: "+mobileOtp);
		
		Integer EmailCacheOTP = emailOtpGenerator.getEmailOPTByKey(key);
		Integer MobileCacheOTP = mobileOtpGenerator.getMobileOPTByKey(key);
		
		System.out.println("EmailCacheOTP:"+EmailCacheOTP);
		System.out.println("MobileCacheOTP:"+MobileCacheOTP);

		if (MobileCacheOTP != null && EmailCacheOTP != null && EmailCacheOTP.equals(emailOtp)
				&& MobileCacheOTP.equals(mobileOtp)) {
			emailOtpGenerator.clearEmailOTPFromCache(key);
			mobileOtpGenerator.clearMobileOTPFromCache(key);
			return true;
		}
		return false;
	}

	public Boolean validateOTP(String key, Integer otp) {

		Integer cacheOTP = otpGenerator.getOPTByKey(key);
		
		System.out.println("cacheOTP: "+cacheOTP);
		
		if (cacheOTP != null && cacheOTP.equals(otp)) {
			otpGenerator.clearOTPFromCache(key);
			return true;
		}
		return false;
	}

}
