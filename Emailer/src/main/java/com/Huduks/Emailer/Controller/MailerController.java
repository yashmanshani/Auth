package com.Huduks.Emailer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Huduks.Emailer.DTO.MailDTO;
import com.Huduks.Emailer.Service.MailerService;

@RestController
@RequestMapping("mailer/v1")
public class MailerController {
	
	@Autowired
	private MailerService service;
	
	@GetMapping("/status")
	public String status() {
		return "Mailer Service Operational";
	}
	
	@PostMapping("/sendEmail")
	public boolean sendEmail(@RequestBody MailDTO mailDetails) {
		return service.sendMail(mailDetails);
	}

}
