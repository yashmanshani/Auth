package com.Huduks.UYSession.Service;

import com.Huduks.UYSession.DTO.NewSession;

public interface SessionService {

	public String getToken(NewSession data);
	public boolean verifyToken(String token);
	public String getEmailFromToken(String token);
}
