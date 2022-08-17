package com.Huduks.UYSession.Utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class HashUtility {
	
	private String key;
	
	public HashUtility(String key) {
		this.key = key;
	}
	
	public String getToken(String header, String payload) {
		String headerBase64 = toBase64(header);
		String payloadBase64 = toBase64(payload);
		String signature = digestSHA256(headerBase64+"."+payloadBase64+"."+key);
		String token = headerBase64+"."+payloadBase64+"."+signature;
		return token;
	}

	public String digestSHA256(String message) {
		String signature="";
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(message.getBytes(StandardCharsets.UTF_8));
			signature = byteToHex(hash);
		}
		catch(NoSuchAlgorithmException e) {}
		return signature;
	}

	private String byteToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder(2*hash.length);
		for(int i=0;i<hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if(hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	private String toBase64(String message) {
		// message => base64
		Encoder encode = Base64.getUrlEncoder();
		String encoded = encode.encodeToString(message.getBytes());
		return encoded;
	}
	
	private String decodeBase64(String message) {
		Decoder decode = Base64.getUrlDecoder();
		byte[] decoded = decode.decode(message.getBytes());
		StringBuilder sb = new StringBuilder();
		for(byte b: decoded) {
			sb.append((char) b);
		}
		return sb.toString();
	}
	
	public boolean verifySignature(String token) {
		String[] tokenParts = token.split("\\.");
		if(tokenParts.length != 3) 
			return false;
		String receivedHeader = tokenParts[0];
		String receivedPayload = tokenParts[1];
		String receivedSignature = tokenParts[2];
		String signature = digestSHA256(receivedHeader+"."+receivedPayload+"."+key);
		
		return receivedSignature.equals(signature);
	}
	
	public String getHeader(String token) {
		String[] tokenParts = token.split("\\.");
		String receivedHeader = tokenParts[0];
		return decodeBase64(receivedHeader);
	}
	
	public String getPayload(String token) {
		String[] tokenParts = token.split("\\.");
		String receivedPayload = tokenParts[1];
		return decodeBase64(receivedPayload);
	}
	
	public String generateSalt(int length) {
		String alphaNumeric = "abcdefghijklmnopqrstuvwxyz"
							+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
							+ "0123456789";
		int len = alphaNumeric.length();
		StringBuilder sb = new StringBuilder();
		Random rand = new Random();
		for (int i=0; i<length; i++) {
			char randomChar = alphaNumeric.charAt(rand.nextInt(len));
			sb.append(randomChar);
		}
		return sb.toString();
	}
}
