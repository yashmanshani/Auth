package com.Huduks.UYSession.Service;

import com.Huduks.UYSession.NewSession;

public interface SessionService {

	public String getToken(NewSession data);
	public String verifyToken(String token);
}
