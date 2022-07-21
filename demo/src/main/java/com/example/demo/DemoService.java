package com.example.demo;

import java.util.List;

public interface DemoService {

	public String isUserValid(LoginCredentialDTO creds);
	public int addition(int a, int b);
	public String registerPostUser(User temp);
	public String updateUser(User user);
	public List<User> getalluser();
	public User getUser(String e);
	public String deleteuser(String e);
	public String addGetUser(String n, String e, String p, String g, int a);
	
}
