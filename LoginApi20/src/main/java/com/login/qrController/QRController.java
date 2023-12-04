package com.login.qrController;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class QRController {

	@Autowired
	QRService qrService;

	@PostMapping("/qr-generator")
	public String processQR() {
		try {
			ResultDTO result = qrService.processQR();
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(result);
		} catch (Exception e) {
			return "Exception found";
		}
	}

	@PostMapping("/qr-verification")
	public String verifyQR() {
		try {
			LoginQRCodeDTO response = qrService.verifyQR();
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(response);
			//return true;
		} catch (Exception e) {
			return "Exception found";
		}
	}

}