package com.Huduks.UYAuth.DTO;

public class NewSession {
	private String email;
	private String deviceId;
	private int days;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	@Override
	public String toString() {
		return "NewSession [email=" + email + ", deviceId=" + deviceId + ", days=" + days + "]";
	}
	
	
	
	
}
