package com.Huduks.Emailer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.Huduks.Emailer.DTO.MailDTO;

@Service
public class MailerServiceImpl implements MailerService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Value("spring.mail.username")
	private String fromEmail;
	
	@Override
	public boolean sendMail(MailDTO mailDto) {
		SimpleMailMessage message = new SimpleMailMessage();
		
        message.setFrom(fromEmail);
        message.setTo(mailDto.getEmail());
        message.setSubject(mailDto.getSubject());
        message.setText(mailDto.getBody());

        mailSender.send(message);
        System.out.println("Mail Send...");
		return true;
	}

}
