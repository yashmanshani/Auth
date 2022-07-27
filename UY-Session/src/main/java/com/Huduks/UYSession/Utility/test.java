package com.Huduks.UYSession.Utility;


import com.Huduks.UYSession.DTO.Payload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class test {

	public static void main(String[] args) {
		String payload = "{\"email\": \"utalmighty@gmail.com\", \"validThru\":151615, \"deviceId\":\"522\"}";
		// String s ={"email": "utalmighty", "date":"05/12/2021"};
		ObjectMapper mapper = new ObjectMapper();
		try {
			Payload obj = mapper.readValue(payload, Payload.class);
			System.out.println(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
