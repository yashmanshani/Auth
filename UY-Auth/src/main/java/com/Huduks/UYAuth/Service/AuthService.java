package com.Huduks.UYAuth.Service;

import java.util.List;

import com.Huduks.UYAuth.DTO.LoginCreds;
import com.Huduks.UYAuth.DTO.UserProfile;

public interface AuthService {
	
	public String addUser(UserProfile user);
	public String updateUser(UserProfile user); //password
	public boolean authenticateUser(LoginCreds creds); // take hashed password
	public String changePasswordInDatabase(String email, String password);
	public List<UserProfile> getAllUser();
	public String forgotPassword(String email);
	public String verifyEmail(String token);
	public String emailVerificationToken(String email);
}
