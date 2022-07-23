package com.Huduks.UYAuth.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Huduks.UYAuth.DTO.UserProfile;
import com.Huduks.UYAuth.Service.AuthService;

@RestController
public class AuthController {
	
	@Autowired
	AuthService service;
	
	@PostMapping("/user")
	public String addUser(@RequestBody UserProfile user) {
		System.out.println(user);
		return service.addUser(user);
	}
	
	@PutMapping("/user")
	public String updateUser(@RequestBody UserProfile user) {
		return service.updateUser(user);
	}
	
	@GetMapping("/users")
	public List<UserProfile> getAllUsers() {
		return service.getAllUser();
	}
}
