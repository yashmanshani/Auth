package com.Huduks.UYSession.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Huduks.UYSession.NewSession;
import com.Huduks.UYSession.Service.SessionService;

@RestController
@RequestMapping("session/v1")
public class ServiceController {
	
	@Autowired
	SessionService service;
	
	@GetMapping("/status")
	public String getStatus() {
		return "Session service operational";
	}
	
	@PostMapping("/create")
	public String createSession(@RequestBody NewSession data) {
		return service.getToken(data);
	}
	
	@GetMapping("/session/{token}")
	public String validateSession(@PathVariable String token) {
		return service.verifyToken(token);
	}
	

}
