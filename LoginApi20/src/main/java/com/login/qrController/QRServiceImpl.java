package com.login.qrController;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.bytebuddy.utility.RandomString;

@Service
public class QRServiceImpl implements QRService {

	@Autowired
	private LoginQRCodeRepository qrRepo;

	@Override
	public ResultDTO processQR() {

		String token = RandomString.make(30);

		LoginQRCode loginqrcode = new LoginQRCode();
		loginqrcode.setId(1);
		loginqrcode.setQrStatus(false);
		loginqrcode.setQrToken(token);
		qrRepo.save(loginqrcode);

		EncryptionService encryptService = new EncryptionService();
		String encryptToken = encryptService.encrypt(token);

		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setQrValue(encryptToken);
		return resultDTO;
	}

	@Override
	public LoginQRCodeDTO verifyQR() {

		Optional<LoginQRCode> user = qrRepo.findById(1);
		Boolean qrStatus = user.get().getQrStatus();
//		LoginQRCode loginQRCode = new LoginQRCode();
//		Boolean qrStatus = loginQRCode.getQrStatus();

		System.out.println("qrStatus: " + qrStatus);

		LoginQRCodeDTO loginQRCodeDTO = new LoginQRCodeDTO();
		loginQRCodeDTO.setQrStatus(qrStatus);

		return loginQRCodeDTO;
	}

}
