package com.Huduks.UYSession.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Huduks.UYSession.Utility.HashUtility;

@Configuration
public class SessionConfiguration {

	@Bean
	public HashUtility getHashObject() {
		return new HashUtility("secretKEY123456");
	}
	
}
