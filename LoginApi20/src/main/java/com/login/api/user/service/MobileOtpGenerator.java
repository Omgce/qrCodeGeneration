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
public class MobileOtpGenerator {

	private static final Integer EXPIRE_MIN = 5;
	private LoadingCache<String, Integer> MobileOtpCache;

	public MobileOtpGenerator() {
		super();
		MobileOtpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Integer>() {
					@Override
					public Integer load(String s) throws Exception {
						return 0;
					}
				});
	}

	public Integer generateMobileOtp(String key) {
		Random random = new Random();
		int OTP = 100000 + random.nextInt(900000);
		MobileOtpCache.put(key, OTP);

		return OTP;
	}

	public Integer getMobileOPTByKey(String key) {
		return MobileOtpCache.getIfPresent(key);
	}

	public void clearMobileOTPFromCache(String key) {
		MobileOtpCache.invalidate(key);
	}

}
