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
public class EmailOtpGenerator {

	private static final Integer EXPIRE_MIN = 5;
	private LoadingCache<String, Integer> EmailOtpCache;

	/**
	 * Constructor configuration.
	 */
	public EmailOtpGenerator() {
		super();
		EmailOtpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Integer>() {
					@Override
					public Integer load(String s) throws Exception {
						return 0;
					}
				});

	}

	/**
	 * Method for generating OTP and put it in cache.
	 *
	 * @param key - cache key
	 * @return cache value (generated OTP number)
	 */
	public Integer generateEmailOtp(String key) {
		Random random = new Random();
		int OTP = 100000 + random.nextInt(900000);
		EmailOtpCache.put(key, OTP);

		return OTP;
	}

	/**
	 * Method for getting OTP value by key.
	 *
	 * @param key - target key
	 * @return OTP value
	 */

	public Integer getEmailOPTByKey(String key) {

		System.out.println("EmailOtpCache.getIfPresent(key):" + EmailOtpCache.getIfPresent(key));

		return EmailOtpCache.getIfPresent(key);
	}

	/**
	 * Method for removing key from cache.
	 *
	 * @param key - target key
	 */
	public void clearEmailOTPFromCache(String key) {
		EmailOtpCache.invalidate(key);
	}

}
