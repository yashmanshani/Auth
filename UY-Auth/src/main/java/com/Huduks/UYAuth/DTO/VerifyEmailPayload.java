package com.Huduks.UYAuth.DTO;

public class VerifyEmailPayload {
	
	private String email;
	private Long validTill;
	
	public Long getValidTill() {
		return validTill;
	}
	public void setValidTill(Long validTill) {
		this.validTill = validTill;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "{\"email\":" + "\"" +email +"\"" + ", \"validTill\":" + validTill + "}";
	}
}
