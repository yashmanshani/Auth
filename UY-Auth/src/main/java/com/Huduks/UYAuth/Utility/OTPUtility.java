package com.Huduks.UYAuth.Utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.Huduks.UYAuth.DTO.OTPDTO;

public class OTPUtility {

	static DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm");
	int timeRoundOff;
	// OTP session valid for timeRoundOff to (timeRoundOff*2)-1
	int otpLength;
	
	public OTPUtility() {
		this(5, 8);
	}
	
	public OTPUtility(int timeRoundOff, int otpLength) {
		if (timeRoundOff <= 0)
			this.timeRoundOff = 5;
		this.timeRoundOff = timeRoundOff;
		if (otpLength<=0) 
			this.otpLength = 8;
		this.otpLength = otpLength;
	}
	
	public OTPDTO getOTP(String message) {
		LocalDateTime time = LocalDateTime.now();
		String otp = generateOTP(message+time);
		int valid = timeRoundOff+((timeRoundOff-(time.getMinute()%timeRoundOff))-1);
		OTPDTO dto = new OTPDTO();
		dto.setOtp(otp);
		dto.setValidtill(valid);
		return dto;
	}
	
	private String generateOTP(String s) {
		int len = otpLength;
		Integer hash = Math.abs(s.hashCode());
		String hashString = hash.toString();
		if(hashString.length() >= len) {
			return hashString.substring(hashString.length()-len);
		}
		len -= hashString.length();
		StringBuilder sb = new StringBuilder(len);
		for(int i=0;i<len; i++) {
			sb.append(0);
		}
		return sb.toString()+hashString;
	} 
	
	private String getRoundOffTime(LocalDateTime time, int roundBy) {
		int minute = time.getMinute();
		LocalDateTime roundToNearestPrevious = time.minusMinutes(minute%roundBy);
		LocalDateTime roundToNearestNext = time.plusMinutes(roundBy-minute%roundBy);
		return roundToNearestPrevious.format(format) + " - " + roundToNearestNext.format(format);
	}
	
	public boolean verifyOtp(String message, String otp) {
		if(otp.length() != otpLength)
			return false;
		LocalDateTime time = LocalDateTime.now();
		String valid = generateOTP(message+getRoundOffTime(time, timeRoundOff));
		if(valid.equals(otp))
			return true;
		time = time.minusMinutes(timeRoundOff);
		valid = generateOTP(message+getRoundOffTime(time, timeRoundOff));
		if(valid.equals(otp))
			return true;
		return false;
	}

}
