package com.login.api.user.service;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.login.api.user.entity.User;
import com.login.api.user.repositories.UserRepository;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

@Service
public class MobileService {

	private final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
	
	@Autowired
	private UserRepository userRepository;

	
	public boolean genrateOTPAndSendOnMobile( String mobile, String motp) throws IOException {
		
		Optional<User> user = userRepository.findByMobile(mobile);
		
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		//MediaType mediaType = MediaType.parse("text/plain");
		MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("text",
				"Dear "+user.get().getUsername()+", Your OTP For  in nifipayments is "+motp+" Valid For 10 Minutes. we request you to don't share with anyone .Thanks NSAFPL")
				.addFormDataPart("mobile", mobile) // Use recipient parameter from request
				.build();
		Request request = new Request.Builder().url("https://nifipayments.com/sandbox/smstest.php").method("POST", body)
				.build();
		 client.newCall(request).execute();

		//return ResponseEntity.ok(response.body().string());
		
		return true;
	}

}
