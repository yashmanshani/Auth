package com.Huduks.UYSession.Service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Huduks.UYSession.NewSession;
import com.Huduks.UYSession.DTO.Header;
import com.Huduks.UYSession.DTO.Payload;
import com.Huduks.UYSession.Utility.HashUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SessionServiceImpl implements SessionService{
	
	@Autowired
	HashUtility util;
	
	@Override
	public String getToken(NewSession data) {
		String email = data.getEmail();
		String deviceId = data.getDeviceId();
		int days = data.getDays();
		
		Header header = new Header();
		Payload payload = new Payload();
		
		header.setName("UY-SessionService");
		
		payload.setEmail(email);
		payload.setDeviceId(deviceId);
		
		LocalDate current = LocalDate.now();
		LocalDate next = current.plusDays(days);
		long epoch = next.toEpochDay();
		payload.setValidThru(epoch);
		
		String token = util.getToken(header.toString(), payload.toString());
		return token;
	}
	
	@Override
	public String verifyToken(String token) {
		if(util.verifySignature(token)) { 
			// No tampering
			String payload = util.getPayload(token);
			System.out.println(payload);
			LocalDate current = LocalDate.now();
			ObjectMapper mapper = new ObjectMapper();
			try {
				Payload obj = mapper.readValue(payload, Payload.class);
				long epoch = obj.getValidThru();
				if(LocalDate.ofEpochDay(epoch).isAfter(current)) {
					return "Session Valid";
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				// return session expired
			}
			
		}
		return "Session Expired";
	}

}