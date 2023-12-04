package com.login.qrController;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QrStatusService {

	@Autowired
	private LoginQRCodeRepository qrRepo;

	public boolean setStatus(String qrToken) {

		Optional<LoginQRCode> existQrToken = qrRepo.findLoginQRCodeByQrToken(qrToken);
		existQrToken.get().setQrStatus(true);
		return true;

	}
	
	

}
