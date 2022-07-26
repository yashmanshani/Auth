package com.Huduks.UYAuth.DTO;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserProfile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@Column
	private String name;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column
	private String password;
	
	@Column
	private boolean verified=false;

	@Column
	private String salt;
	
	
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	
	public Long getUserId() {
		return userId;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String generateSalt(int length) {
		String alphaNumeric = "abcdefghijklmnopqrstuvwxyz"
							+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
							+ "0123456789";
		int len = alphaNumeric.length();
		StringBuilder sb = new StringBuilder();
		Random rand = new Random();
		for (int i=0; i<length; i++) {
			char randomChar = alphaNumeric.charAt(rand.nextInt(len));
			sb.append(randomChar);
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return "UserProfile [userId=" + userId + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", verified=" + verified + ", salt=" + salt + "]";
	}

}
