package com.Huduks.UYSession.DTO;

public class Payload {
	
	private String email;
	private Long validThru;
	private String deviceId;
	private Long issuedOn;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getValidThru() {
		return validThru;
	}
	public Long getIssuedOn() {
		return issuedOn;
	}
	public void setIssuedOn(Long issuedOn) {
		this.issuedOn = issuedOn;
	}
	public void setValidThru(Long validThru) {
		this.validThru = validThru;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Override
	public String toString() {
		return "{\"email\":" + "\"" +email +"\""+ ", \"issuedOn\":" + issuedOn + ", \"validThru\":" + validThru + ", \"deviceId\":" + "\"" +deviceId + "\"" + "}";
	}
	
	

}
