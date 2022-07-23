package com.Huduks.UYAuth.Service;

import java.util.List;

import com.Huduks.UYAuth.DTO.LoginCreds;
import com.Huduks.UYAuth.DTO.UserProfile;

public interface AuthService {
	
	public String addUser(UserProfile user);
	public String updateUser(UserProfile user); //password
	public boolean authenticateUser(LoginCreds creds); // take hashed password
	public String changePassword(UserProfile user);
	public List<UserProfile> getAllUser();
	
}
