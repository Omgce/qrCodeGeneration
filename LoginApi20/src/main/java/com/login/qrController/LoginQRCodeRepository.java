package com.login.qrController;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginQRCodeRepository extends JpaRepository<LoginQRCode, Integer> {

	Optional<LoginQRCode> findLoginQRCodeByQrToken(String qrToken);

}