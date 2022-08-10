package com.Huduks.UYAuth.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Huduks.UYAuth.DTO.ChangePasswordDTO;
import com.Huduks.UYAuth.DTO.LoginCreds;
import com.Huduks.UYAuth.DTO.UserDTO;
import com.Huduks.UYAuth.DTO.UserProfile;
import com.Huduks.UYAuth.Service.AuthService;

@RestController
@RequestMapping("/auth/v1")
public class AuthController {
	
	@Autowired
	AuthService service;
	
	@GetMapping("/status")
	public String getStatus() {
		return "All Service Operational";
	}
	
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
	
	@PostMapping("/login")
	public UserDTO loginUser(@RequestBody LoginCreds creds) {
		return service.authenticateUser(creds);
	}
	
	@GetMapping("/verifyEmail/{token}")
	public String verifyEmail(@PathVariable String token) {
		return service.verifyEmail(token);
	}
	
	@GetMapping("/processVerification/{email}")
	public String sendEmailForVerification(@PathVariable String email) {
		return service.emailVerificationToken(email);
	}
	
	@GetMapping("/forgetPassword/{email}")
	public String forgetPassword(@PathVariable String email) {
		return service.forgotPassword(email);
	}
	
	@PostMapping("/verifyOTP")
	public boolean verifyOTP(@RequestBody ChangePasswordDTO otpCreds) {
		String email =  otpCreds.getEmail();
		String otp = otpCreds.getOtp();
		
		return service.verifyOTP(email, otp);
	}

	@PostMapping("/verifyAndChangePassword")
	public String verifyAndChangePassword(@RequestBody ChangePasswordDTO userCreds) {
		String email =  userCreds.getEmail();
		String password = userCreds.getPassword();
		String otp = userCreds.getOtp();
		return service.verifyAndChangePassword(email, password, otp);
	}

}
