package com.example.demo;

public class Demo {

	public static void main(String[] args) {
		User u = new User();
		u.setName("utkarsh");
		u.setEmail("uta@jbdfjds.com");
		u.setGender("male");
		u.setPassword("password");
		u.setAge(15);
		System.out.println(u); // by default toString()
	}
}
