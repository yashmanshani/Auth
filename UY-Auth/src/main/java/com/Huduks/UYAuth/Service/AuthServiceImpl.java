package com.Huduks.UYAuth.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Huduks.UYAuth.DTO.LoginCreds;
import com.Huduks.UYAuth.DTO.UserProfile;
import com.Huduks.UYAuth.Repository.AuthRepository;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	AuthRepository repo;

	@Override
	public String addUser(UserProfile user) {
		// Saves the data in the database.
		try {
			repo.save(user);
			return "User Added Successfully";
		}
		catch(Exception e) {
			return "Error: "+e.getLocalizedMessage();
		}
	}

	@Override
	public String updateUser(UserProfile user) {
		//one valuen
		String email = user.getEmail();
		UserProfile temp = repo.findUserProfileByEmail(email);
		if (temp == null) {
			return "No such user with email "+email;
		}
		if (temp.isVerified() == false) {
			return "User verfication Pending";
		}
		user.setUserId(temp.getUserId());
		user.setPassword(temp.getPassword());
		user.setVerified(temp.isVerified());
		repo.save(user);
		return "User Updated Successfully";
	}

	@Override
	public boolean authenticateUser(LoginCreds creds) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String changePassword(UserProfile user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserProfile> getAllUser() {
		return (List<UserProfile>) repo.findAll();
	}

}
