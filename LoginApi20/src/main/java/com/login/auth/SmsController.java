package com.login.auth;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RestController
public class SmsController {

	@PostMapping("/send-sms")
	public ResponseEntity<String> sendSms(@RequestParam String recipient, @RequestParam String otp) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("text",
				"Dear {#var#} Your OTP For {#var#} in {#var#} is {#var#} Valid For 10 Minutes. we request you to don't share with anyone .Thanks NSAFPL")
				.addFormDataPart("mobile", recipient) // Use recipient parameter from request
				.build();
		Request request = new Request.Builder().url("https://nifipayments.com/sandbox/smstest.php").method("POST", body)
				.build();
		Response response = client.newCall(request).execute();

		return ResponseEntity.ok(response.body().string());
	}

}
