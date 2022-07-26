package com.Huduks.UYAuth.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.Huduks.UYAuth.Utility.HashUtility;
import com.Huduks.UYAuth.Utility.OTPUtility;

@Configuration
public class UYAuthConfig {

	@Bean
	public HashUtility getObject() {
		return new HashUtility("secret123456");
	}
	
	@Bean
	public RestTemplate getTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public OTPUtility getOTPObject() {
		return new OTPUtility(5, 8);
	}
}
