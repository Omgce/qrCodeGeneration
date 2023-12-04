package com.login.api.jwt;

import java.util.Date;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.login.api.user.entity.User;
import com.login.api.user.repositories.UserRepository;
import com.login.api.user.service.OtpService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import net.bytebuddy.utility.RandomString;

@Component
public class TokenProvider {

	private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

	private static final String AUTHORITIES_KEY = "auth";

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long tokenValidityInSeconds;

	@Value("${jwt.expiration}")
	private long tokenValidityInSecondsForRememberMe;

	@Autowired
	private OtpService otpService;

	@Autowired
	private UserRepository userRepository;

	public String createToken(Authentication authentication, Boolean rememberMe) {

		System.out.println("Authentication: " + authentication);

		String email = authentication.getName();
		
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("User with Email " + email + " not found!"));


		//System.out.println("GetName: " + email);
		otpService.generateOtp(user.getEmail());

		String token = RandomString.make(30);
		return token;
		
	//	return generateToken(authentication, rememberMe);

	}

	public String createTokenAfterVerifiedOtp(String email, Boolean rememberMe) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("Email not found!"));

		Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

		return generateToken(authentication, rememberMe);

	}
	
	public Authentication getAuthentication(String token) {

		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

		String principal = claims.getSubject();

		return new UsernamePasswordAuthenticationToken(principal, "", null);
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature: {}", e.getMessage());
			return false;
		}
	}

	private String generateToken(Authentication authentication, Boolean rememberMe) {

		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		long now = new Date().getTime();
		Date validity;

		if (Boolean.TRUE.equals(rememberMe)) {
			validity = new Date(now + this.tokenValidityInSecondsForRememberMe * 1000);
		} else {
			validity = new Date(now + this.tokenValidityInSeconds * 1000);
		}

		return Jwts.builder().setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities)
				.signWith(SignatureAlgorithm.HS512, secretKey).setExpiration(validity).compact();
	}

}