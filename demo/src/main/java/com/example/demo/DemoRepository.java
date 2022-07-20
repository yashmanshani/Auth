package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class DemoRepository {
	
	List<User> database = new ArrayList<>();
	// CRUD
	
	// create/add
	public String adduser(User user) {
		database.add(user);
		return "User Added to database";
	}
	
	// read
	public User getUser(String email) {
		for (int i=0; i<database.size(); i++) {
			User temp = database.get(i);
			if(temp.getEmail().equals(email)) {
				return temp;
			}
		}
		return null;
	}
	
	//readalldata 
	public List<User> getalluser(){
		
		return database;
	}
	
	// update 
	// write a function to update user
	
	// delete
	public String deleteuser(String email) {
		for (int i=0; i<database.size(); i++) {
			User temp = database.get(i);
			if(temp.getEmail().equals(email)) {
				database.remove(i);
				return "User deleted successfully";
			}
		}
		return "ERROR: User with email:" + email + "not found";
	}
	
}
