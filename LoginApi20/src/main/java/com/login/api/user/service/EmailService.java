package com.login.api.user.service;

import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.login.api.user.dto.EmailDTO;

@Service
public class EmailService {

	private final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	private JavaMailSender emailSender;

	/**
	 * Method for sending simple e-mail message.
	 * 
	 * @param emailDTO - data to be send.
	 */
	public Boolean sendSimpleMessage(EmailDTO emailDTO) {

		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom("mromraj99@gmail.com");
			mimeMessageHelper.setTo(emailDTO.getRecipients().stream().collect(Collectors.joining(",")));
			mimeMessageHelper.setSubject(emailDTO.getSubject());
			mimeMessageHelper.setText(emailDTO.getBody());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// SimpleMailMessage mailMessage = new SimpleMailMessage();

		Boolean isSent = false;
		try {
			// emailSender.send(mailMessage);
			emailSender.send(mimeMessage);
			isSent = true;
		} catch (Exception e) {
			LOGGER.error("Sending e-mail error: {}", e.getMessage());
		}
		return isSent;
	}

}
