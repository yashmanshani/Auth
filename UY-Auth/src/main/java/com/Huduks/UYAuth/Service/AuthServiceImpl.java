package com.Huduks.UYAuth.Service;

import java.util.List;
import java.util.Optional;

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
			user.setUserId(null);
			user.setVerified(false);
			user.setSalt(null); // TODO: changes required
			repo.save(user);
			return "User Added Successfully";
		}
		catch(org.springframework.dao.DataIntegrityViolationException e) {
			return "Email Id already exist";
		}
		catch(Exception e) {
			return "Error: "+e.getLocalizedMessage();
		}
	}

	@Override
	public String updateUser(UserProfile user) {
		//one valnernability --> 
//		String email = user.getEmail();
//		UserProfile temp = repo.findUserProfileByEmail(email);
//		if (temp == null) {
//			return "No such user with email "+email;
//		}
//		if (temp.isVerified() == false) {
//			return "User verfication Pending";
//		}
//		user.setUserId(temp.getUserId());
//		user.setPassword(temp.getPassword());
//		user.setVerified(temp.isVerified());
//		repo.save(user);
//		return "User Updated Successfully";
		return null;
	}

	@Override
	public boolean authenticateUser(LoginCreds creds) {
		String email = creds.getEmail();
		String password = creds.getPassword();
		
		Optional<UserProfile> opt = repo.findUserProfileByEmail(email);
		
		if(opt.isEmpty()) {
			return false;
		}
		UserProfile temp = opt.get();
		return password.equals(temp.getPassword());
	}

	@Override
	public String forgotPassword(String email) {
		// TODO Auto-generated method stub
		
		// email -> mail (OTP)
		// email->otp -> then he should call other endpoint usme email/otp 
		return null;
	}

	@Override
	public List<UserProfile> getAllUser() {
		return (List<UserProfile>) repo.findAll();
	}

	@Override
	public String changePasswordInDatabase(String email, String password) {
		Optional<UserProfile> opt = repo.findUserProfileByEmail(email);
		if (opt.isEmpty()) {
			return "Invalid Email";
		}
		UserProfile temp = opt.get();
		temp.setPassword(password);
		repo.save(temp);
		// End all sessions 
		return "Password Changed Successfully";
	}

}
