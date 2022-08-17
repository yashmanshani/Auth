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
import com.Huduks.UYAuth.DTO.EmailDTO;
import com.Huduks.UYAuth.DTO.LoginCreds;
import com.Huduks.UYAuth.DTO.NewSession;
import com.Huduks.UYAuth.DTO.OTPDTO;
import com.Huduks.UYAuth.DTO.TokenDTO;
import com.Huduks.UYAuth.DTO.UserDTO;
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
	
	int saltLength = 8;
	int sessionForDays = 5;
	String mailerUrl = "http://localhost:6971/mailer/v1/sendEmail";
	String sessionUrl = "http://localhost:6970/session/v1";
	
	// database password storage format hashSHA256 (password+salt)
	// hello -> hash(hello+dgjb5f2w) -> hfvdsfhdsjfkbdsgbs
	// hello -> hash(hello+dgjb5f2w) -> hfvdsfhdsjfkbdsgbs
	private int expiryMinutes = 10;
	ZoneId zone = ZoneId.of("Asia/Kolkata");
	
	
	@Override
	public String addUser(UserProfile user) {
		// Saves the data in the database.
		try {
			user.setUserId(null);
			user.setVerified(false);
			String salt = hash.generateSalt(saltLength);
			user.setSalt(salt);
			String password = user.getPassword();
			user.setPassword(hash.digestSHA256(password+salt));
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
	public String updateUser(UserDTO user) {
		String email = user.getEmail();
		String token = user.getToken();
		String name = user.getName();
		if(email == null || email.isBlank()) {
			return "Email ID can not be empty";
		}
		if(name == null || name.isBlank()) {
			return "Name can not be empty";
		}
		
		if(authenticateUserViaSession(token)) {
			String getEmailUrl = sessionUrl+"/getEmail";
			TokenDTO tokenDTO = new TokenDTO();
			tokenDTO.setToken(token);
			EmailDTO response = template.postForObject(getEmailUrl, tokenDTO, EmailDTO.class);
			String tokenEmail = response.getEmail();
			if(tokenEmail.equals(email)) {
				Optional<UserProfile> opt = repo.findUserProfileByEmail(email);
				
				if (opt.isEmpty()) {
					return "No such user with email "+email;
				}
				UserProfile temp = opt.get();
				if (temp.isVerified() == false) {
					return "User verfication Pending";
				}
				temp.setName(name);
				repo.save(temp);
				return "User Updated Successfully";
			}
		}
		return "Session Invalid/Expired";
		
		
	}
	
	@Override
	public List<UserProfile> getAllUser() {
		return (List<UserProfile>) repo.findAll();
	}
	
	public UserDTO getUserProfile(String email, String token) {
		Optional<UserProfile> opt = repo.findUserProfileByEmail(email);
		if(opt.isEmpty()) {
			return null;
		}
		UserProfile user = opt.get();
		UserDTO dto = new UserDTO();
		if(user.isVerified()) {
			dto.setEmail(user.getEmail());
			dto.setName(user.getName());
			dto.setToken(token);
			dto.setMessage("Login successful");
			return dto;
		}
		dto.setMessage("User Not Verified");
		return dto;
	}
	
	
	// Authenticating User/Login
	@Override
	public UserDTO authenticateUser(LoginCreds creds) {
		//{email:"", pass: "", token: "dgfjebvierbverbgieorbge.rghehrhthrthh.sffewfewfwf"}
		String token = creds.getToken();
		
		if (token != null && !token.isBlank() && authenticateUserViaSession(token)) {
				String getEmailUrl = sessionUrl+"/getEmail";
				TokenDTO tokenDTO = new TokenDTO();
				tokenDTO.setToken(token);
				EmailDTO response = template.postForObject(getEmailUrl, tokenDTO, EmailDTO.class);
				String email = response.getEmail();
				UserDTO dto = getUserProfile(email, token);
				return dto;
		}
		else {
			String email = creds.getEmail();
			String password = creds.getPassword();
			if (email != null && password != null && !email.isBlank() && !password.isBlank()) {
				if(authenticateUserViaEmailPassword(email, password)) {
					String newSessionUrl = sessionUrl+"/create";
					NewSession sessionObj = new NewSession();
					sessionObj.setEmail(email);
					sessionObj.setDeviceId("DEFAULT"); // TODO: Change later
					sessionObj.setDays(sessionForDays);
					TokenDTO tokenDTO =  template.postForObject(newSessionUrl, sessionObj, TokenDTO.class);
					String newToken = tokenDTO.getToken();
					UserDTO dto = getUserProfile(email, newToken);
					return dto;
				}
				else {
					UserDTO dto = new UserDTO();
					dto.setMessage("Invalid Credentials");
					return dto;
				}
			}
			else {
				UserDTO dto = new UserDTO();
				dto.setMessage("Session Invalid/Expired");
				return dto;
			}
		}
	}

	private boolean authenticateUserViaEmailPassword(String email, String password) {
		
		Optional<UserProfile> opt = repo.findUserProfileByEmail(email);
		
		if(opt.isEmpty()) {
			return false;
		}
		UserProfile temp = opt.get();
		
		String hashedPassword = hash.digestSHA256(password+temp.getSalt());
		return hashedPassword.equals(temp.getPassword());
	}
	
	private boolean authenticateUserViaSession(String token) {
		String validateUrl = sessionUrl+"/session"; //=> http://localhost:6970/session/v1/session
		TokenDTO tokenDTO = new TokenDTO();
		tokenDTO.setToken(token);
		boolean response = template.postForObject(validateUrl, tokenDTO, Boolean.class);
		return response;
	}

	
	// Forgot password
	@Override
	public String forgotPassword(String email) {
		Optional<UserProfile> opt = repo.findUserProfileByEmail(email);
		if (opt.isEmpty()) {
			return "User does not exist";
		}
		
		UserProfile user = opt.get();
		if(!user.isVerified()) {
			return "User not verified";
		}
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
		
		if(!user.isVerified()) {
			return false;
		}
		
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
		String hashedPassword = hash.digestSHA256(password+temp.getSalt());
		temp.setPassword(hashedPassword);
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
