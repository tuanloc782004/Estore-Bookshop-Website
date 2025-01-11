package com.estorebookshop.config.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.estorebookshop.config.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
    private JavaMailSender mailSender;
	
	@Override
	public void sendEmail(String to, String subject, String content) throws MessagingException {
		// TODO Auto-generated method stub
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(content, true); // `true` để email hỗ trợ HTML

		mailSender.send(message);
	}

}
