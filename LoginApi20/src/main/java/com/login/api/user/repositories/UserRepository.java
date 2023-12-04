package com.login.api.user.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.login.api.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	Optional<User> findByMobile(String mobile);

	Optional<User> findBySendOtpToken(String sendOtpToken);

	Optional<User> findBySendPasswordToken(String sendPasswordToken);

	Optional<User> findBySendPinToken(String sendPinToken);

	Optional<User> findBySendLoginToken(String sendLoginToken);

	Optional<User> findByAuthToken(String authToken);

	Optional<User> findByPin(String pin);

	Optional<User> findByDeviceId(String deviceId);

	@Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.email = ?2")
	@Modifying
	public void updateFailedAttempts(int failAttempts, String email);

}
