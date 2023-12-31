package com.login.api.user.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Description(value = "Service for generating and validating OTP.")
@Service
public class OtpGenerator {

	private static final Integer EXPIRE_SECONDS = 30;
	private LoadingCache<String, Integer> otpCache;

	/**
	 * Constructor configuration.
	 */
	public OtpGenerator() {
		super();
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_SECONDS, TimeUnit.SECONDS)
				.build(new CacheLoader<String, Integer>() {
					@Override
					public Integer load(String s) throws Exception {
						return 0;
					}
				});
	}

	public Integer generateOTP(String key) {

		Random random = new Random();
		int first = random.nextInt(8) + 1;
		int second = random.nextInt(8) + 1;
		int third = random.nextInt(8) + 1;
		int fourth = random.nextInt(8) + 1;
		int fifth = random.nextInt(8) + 1;
		int sixth = random.nextInt(8) + 1;
		int seventh = random.nextInt(8) + 1;
		String generateOtp = String.format("%d%d%d%d%d%d%d", first, second, third, fourth, fifth, sixth, seventh);

//		byte[] byteArray = otp.getBytes();
//
//		String base64EncodedFirst = Base64.getEncoder().encodeToString(byteArray);
//
//		byte[] byteArrayFirst = base64EncodedFirst.getBytes();
//
//		String base64EncodedSecond = Base64.getEncoder().encodeToString(byteArrayFirst);
//
//		String substring = base64EncodedSecond.substring(base64EncodedSecond.length() - 4);
//		String substring2 = base64EncodedSecond.substring(0, base64EncodedSecond.length() - 4);
//
//		String substring3 = substring + substring2;
//
//		byte[] byteArraySecond = substring3.getBytes();
//
//		String base64EncodedThird = Base64.getEncoder().encodeToString(byteArraySecond);
//
//		System.out.println("base64EncodedThird: " + base64EncodedThird);
//		return base64EncodedThird;

		Integer OTP = Integer.valueOf(generateOtp);

		otpCache.put(key, OTP);
		return OTP;

	}

	public Integer getOPTByKey(String key) {
		return otpCache.getIfPresent(key);
	}

	public void clearOTPFromCache(String key) {
		otpCache.invalidate(key);
	}
}
