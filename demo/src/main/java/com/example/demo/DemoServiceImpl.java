package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl {
	
	@Autowired
	DemoRepository repo;
	
	public String isUserValid(LoginCredentialDTO creds) {
		if (repo.valid(creds.getEmail(), creds.getPassword()) == true) {
			return "YES VALID USER";
		}
		else {
			return "NOT VALID USER";
		}
	}
	
	public int addition(int a, int b) {
		int sum = a+b;
		return sum;
	}
	
	public String registerPostUser(User temp) {
		// check if email is unique
		String email = temp.getEmail();
		User u = repo.getUser(email);
		if(u == null) { 
			// we can register new user
			return repo.adduser(temp);
		}
		else {
			return "Email Id already registered";
		}
	}
	
	public String updateUser(User user) {
		return repo.updateUser(user);
	}

	public List<User> getalluser() {
		return repo.getalluser();
	}

	public User getUser(String e) {
		return repo.getUser(e);
	}

	public String deleteuser(String e) {
		return repo.deleteuser(e);
	}

	public String addGetUser(String n, String e, String p, String g, int a) {
		User object = new User();
		object.setName(n);
		object.setEmail(e);
		object.setPassword(p);
		object.setGender(g);
		object.setAge(a);
		return registerPostUser(object);
	}
	
	
	

}
