package com.Huduks.Emailer.Service;

import com.Huduks.Emailer.DTO.MailDTO;

public interface MailerService {
	
	public boolean sendMail(MailDTO mailDto);

}
