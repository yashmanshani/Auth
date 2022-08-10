package com.Huduks.UYAuth.Service;

import java.util.List;

import com.Huduks.UYAuth.DTO.LoginCreds;
import com.Huduks.UYAuth.DTO.UserDTO;
import com.Huduks.UYAuth.DTO.UserProfile;

public interface AuthService {
	
	public String addUser(UserProfile user);
	public String updateUser(UserProfile user); //password
	
	public UserDTO authenticateUser(LoginCreds creds);
	public boolean authenticateUserViaEmailPassword(String email, String password); // take hashed password
	public boolean authenticateUserViaSession(String token);
	
	public String changePasswordInDatabase(String email, String password);
	public List<UserProfile> getAllUser();
	
	public String forgotPassword(String email);
	public boolean verifyOTP(String email, String otp);
	public String verifyAndChangePassword(String email, String password, String otp);
	
	public String verifyEmail(String token);
	public String emailVerificationToken(String email);
	
}
