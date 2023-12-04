package com.login.demo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class PasswordDecode {

	 public static void main(String[] args) {
	        String targetHash = "5a105e8b9d40e1329780d62ea2265d8a"; // MD5 hash of "hello"
	        
	        // Assume we're trying to find a 4-letter lowercase password
	        int passwordLength = 4;
	        char[] charset = "abcdefghijklmnopqrstuvwxyz".toCharArray();

	        try {
	            MessageDigest md = MessageDigest.getInstance("MD5");

	            for (String password : generatePasswords(passwordLength, charset)) {
	                md.update(password.getBytes());
	                byte[] bytes = md.digest();

	                StringBuilder sb = new StringBuilder();
	                for (byte b : bytes) {
	                    sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
	                }

	                if (sb.toString().equals(targetHash)) {
	                    System.out.println("Password found: " + password);
	                    return;
	                }
	            }

	            System.out.println("Password not found.");
	        } catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
	        }
	    }

	    private static Iterable<String> generatePasswords(int length, char[] charset) {
	        // Recursive method to generate all possible passwords of a given length
	        if (length == 1) {
	            List<String> passwords = new ArrayList<>();
	            for (char c : charset) {
	                passwords.add(String.valueOf(c));
	            }
	            return passwords;
	        } else {
	            List<String> passwords = new ArrayList<>();
	            for (String partial : generatePasswords(length - 1, charset)) {
	                for (char c : charset) {
	                    passwords.add(partial + c);
	                }
	            }
	            return passwords;
	        }
	    }
	
}
