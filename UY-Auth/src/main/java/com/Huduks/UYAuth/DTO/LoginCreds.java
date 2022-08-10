package com.Huduks.UYAuth.DTO;

public class LoginCreds {
	private String email;
	private String password;
	private String token;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
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
	@Override
	public String toString() {
		return "LoginCreds [email=" + email + ", password=" + password + ", token=" + token + "]";
	}
	
}
