package com.login.demo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncode {

	public static void main(String[] args) {

//		PasswordEncoder pass = new BCryptPasswordEncoder();
//
//		String password = pass.encode("123456");

		 String password="Rahul@123";

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] bytes = md.digest();

			StringBuilder sb = new StringBuilder();
			for (byte b : bytes) {
				sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
			}

			System.out.println(sb.toString());
			// return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

	}

}
