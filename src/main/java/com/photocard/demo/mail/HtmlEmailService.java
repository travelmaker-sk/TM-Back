package com.photocard.demo.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class HtmlEmailService implements EmailService{

	private final JavaMailSender javaMailSender;
	
	@Override
	public void sendEmail(EmailMessage emailMessage) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,false,"UTF-8");
			messageHelper.setTo(emailMessage.getTo());
			messageHelper.setSubject(emailMessage.getSubject());
			messageHelper.setText(emailMessage.getMessage(),true);
			javaMailSender.send(mimeMessage);
			log.info(emailMessage.getMessage());
		} catch (MessagingException e) {
			log.error("sent email : {} ", e);
		}
	}

}
