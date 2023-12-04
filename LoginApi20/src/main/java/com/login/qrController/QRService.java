package com.login.qrController;

public interface QRService {
	ResultDTO processQR();
	LoginQRCodeDTO verifyQR();
	
}
