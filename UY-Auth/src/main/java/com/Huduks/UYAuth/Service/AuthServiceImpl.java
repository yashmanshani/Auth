package com.Huduks.UYAuth.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.Huduks.UYAuth.DTO.AuthMailDTO;
import com.Huduks.UYAuth.DTO.LoginCreds;
import com.Huduks.UYAuth.DTO.OTPDTO;
import com.Huduks.UYAuth.DTO.UserProfile;
import com.Huduks.UYAuth.DTO.VerifyEmailHeader;
import com.Huduks.UYAuth.DTO.VerifyEmailPayload;
import com.Huduks.UYAuth.Repository.AuthRepository;
import com.Huduks.UYAuth.Utility.HashUtility;
import com.Huduks.UYAuth.Utility.OTPUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	AuthRepository repo;
	
	@Autowired
	RestTemplate template;
	
	@Autowired
	HashUtility hash;
	
	@Autowired
	OTPUtility otp;
	
	String mailerUrl = "http://localhost:6971/mailer/v1/sendEmail";
	
	
	private int expiryMinutes = 10;
	ZoneId zone = ZoneId.of("Asia/Kolkata");
	

	@Override
	public String addUser(UserProfile user) {
		// Saves the data in the database.
		try {
			user.setUserId(null);
			user.setVerified(false);
			user.setSalt(null); // TODO: changes required
			repo.save(user);
			emailVerificationToken(user.getEmail());
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
	public List<UserProfile> getAllUser() {
		return (List<UserProfile>) repo.findAll();
	}

	
	// Forgot password
	@Override
	public String forgotPassword(String email) {
		Optional<UserProfile> opt = repo.findUserProfileByEmail(email);
		if (opt.isEmpty()) {
			return "User does not exist";
		}
		
		UserProfile user = opt.get();
		String password = user.getPassword();
		
		String key = email+password;
		
		OTPDTO otpDto = otp.getOTP(key);
		
		String body = "OTP for Forgot Password is: "+otpDto.getOtp()+ " \n This is Valid for next "+ otpDto.getValidtill() + " minutes.";
		AuthMailDTO mailDto = new AuthMailDTO();
		mailDto.setEmail(email);
		mailDto.setBody(body);
		mailDto.setSubject("HUDUK Auth Forgot Password ");
		
		
		boolean mailStatus = template.postForObject(mailerUrl, mailDto, Boolean.class);
		if(mailStatus == true) {
			return "Mail sent successfully";
		}
		else {
			return "Mail not sent";
		}
	}
	
	@Override
	public boolean verifyOTP(String email, String otpString) {
		Optional<UserProfile> opt = repo.findUserProfileByEmail(email);
		if (opt.isEmpty()) {
			return false;
		}
		
		UserProfile user = opt.get();
		String password = user.getPassword();
		
		String key = email+password;
		
		return otp.verifyOtp(key, otpString);
	}

	@Override
	public String verifyAndChangePassword(String email, String password, String otp) {
		if(verifyOTP(email, otp)) {
			changePasswordInDatabase(email, password);
			return "Password Changed Successfully";
		}
		return "Password change unsuccessful";
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
	
	
	// Email verification
	@Override
	public String verifyEmail(String token) {
		// token = base64(data)hash(data+secret)
		if(hash.verifySignature(token)) {
			String payload = hash.getPayload(token);
			LocalDateTime timeNow = LocalDateTime.now();
			ZoneOffset zoneOffSet = zone.getRules().getOffset(timeNow);
			System.out.println(payload);
			ObjectMapper mapper = new ObjectMapper();
			try {
				VerifyEmailPayload obj = mapper.readValue(payload, VerifyEmailPayload.class);
				long epoch = obj.getValidTill();
				// payload=10:10:00s is after now = 10:11:00secs 
				if(LocalDateTime.ofEpochSecond(epoch, 0, zoneOffSet).isBefore(timeNow)) {
					return "Session Expired";
				}
				setEmailVerifiedTrue(obj.getEmail());
				return obj.getEmail()+" verification Successfull";
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return "Token Signature Not Valid";	
	}

	private void setEmailVerifiedTrue(String email) {
		Optional<UserProfile> opt = repo.findUserProfileByEmail(email);
		UserProfile user = opt.get();
		user.setVerified(true);
		repo.save(user);
	}

	@Override
	public String emailVerificationToken(String email) {
		Optional<UserProfile> opt = repo.findUserProfileByEmail(email);
		if(opt.isEmpty()) {
			return "No Such User";
		}
		VerifyEmailHeader header = new VerifyEmailHeader();
		header.setName("UY-EmailVerification");
		
		VerifyEmailPayload payload = new VerifyEmailPayload();
		payload.setEmail(email);
		
		LocalDateTime timeNow = LocalDateTime.now();
		LocalDateTime till = timeNow.plusMinutes(expiryMinutes);
		ZoneOffset zoneOffSet = zone.getRules().getOffset(timeNow);
		payload.setValidTill(till.toEpochSecond(zoneOffSet));
		
		String token = hash.getToken(header.toString(), payload.toString());
		String link = "http://localhost:6969/auth/v1/verifyEmail/"+token;
		AuthMailDTO mailDto = new AuthMailDTO();
		mailDto.setEmail(email);
		mailDto.setBody(link + "\n This verification code is valid till 10 Minutes.");
		mailDto.setSubject("HUDUK Auth verification Mail");
		
		boolean mailStatus = template.postForObject(mailerUrl, mailDto, Boolean.class); // 
		if(mailStatus == true) {
			return "Mail sent successfully";
		}
		else {
			return "Mail not sent";
		}
	}

}
