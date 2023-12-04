package com.login.api.security;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.login.api.exceptions.UserNotActivatedException;
import com.login.api.user.entity.User;
import com.login.api.user.repositories.UserRepository;

/**
 * Authenticate a user from the database.
 */
public class CustomUserDetailsService implements UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String login) {
		log.debug("Authenticating {}", login);
		String lowercaseLogin = login.toLowerCase();

		Optional<User> userFromDatabase = userRepository.findByEmail(lowercaseLogin);
		return userFromDatabase.map(user -> {
			if (user.getEnabled() == null || !user.getEnabled()) {
				log.warn("User " + lowercaseLogin + " was not activated");
				throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
			}
			return new CustomUserDetails(user);
		}).orElseThrow(() -> {
			log.warn("User " + lowercaseLogin + " was not found in the database");
			return new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database");
		});

	}

}
