package com.Huduks.UYSession.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Huduks.UYSession.DTO.EmailDto;
import com.Huduks.UYSession.DTO.NewSession;
import com.Huduks.UYSession.DTO.TokenDTO;
import com.Huduks.UYSession.Service.SessionService;

@RestController
@RequestMapping("session/v1")
public class SessionController {
	
	@Autowired
	SessionService service;
	
	@GetMapping("/status")
	public String getStatus() {
		return "Session service operational";
	}
	
	@PostMapping("/create")
	public TokenDTO createSession(@RequestBody NewSession data) {
		String token = service.getToken(data);
		TokenDTO tokenDto = new TokenDTO();
		tokenDto.setToken(token);
		return tokenDto;
	}
	
	@PostMapping("/session")
	public boolean validateSession(@RequestBody TokenDTO tokenDto) {
		String token = tokenDto.getToken();
		return service.verifyToken(token);
	}
	
	@PostMapping("/getEmail")
	public EmailDto getEmailFromSession(@RequestBody TokenDTO tokenDto) {
		String email = service.getEmailFromToken(tokenDto.getToken());
		EmailDto dto = new EmailDto();
		dto.setEmail(email);
		return dto;
	}

}

// 
