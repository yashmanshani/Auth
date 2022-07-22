package com.example.demo.DTO;

public class Demo {

	public static void main(String[] args) {
		Student u = new Student();
		u.setName("utkarsh");
		u.setEmail("uta@jbdfjds.com");
		u.setGender("male");
		u.setPassword("password");
		u.setAge(15);
		System.out.println(u); // by default toString()
	}
}
