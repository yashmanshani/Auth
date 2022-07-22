package com.example.demo.Repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.DTO.Student;

@Repository
public class DemoRepository {
	
	List<Student> database = new ArrayList<>();
	// CRUD
	
	// create/add
	public String adduser(Student user) {
		database.add(user);
		return "Student Added to database";
	}
	
	public boolean valid(String email,String pass) {
		for (int i=0; i<database.size(); i++) {
			Student s = database.get(i);
			if(s.getEmail().equals(email)) {
				if(s.getPassword().equals(pass)) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		return false;
	}
	
	// read
	public Student getStudent(String email) {
		for (int i=0; i<database.size(); i++) {
			Student s = database.get(i);
			if(s.getEmail().equals(email)) {
				return s;
			}
		}
		return null;
	}
	
	//readalldata 
	public List<Student> getalluser(){
		
		return database;
	}
	
	// update 
	// write a function to update user
	public String updateStudent(Student user) {
		String email = user.getEmail();
		for (int i=0; i<database.size(); i++) {
			Student s = database.get(i);
			if (email.equals(s.getEmail())) { //user found
				s.setAge(user.getAge());
				s.setGender(user.getGender());
				s.setPassword(user.getPassword());
				return "Student updated successfully";
			}
		}
		return "Student not found by Email "+email;
		
	}
	
	
	// delete
	public String deleteuser(String email) {
		for (int i=0; i<database.size(); i++) {
			Student temp = database.get(i);
			if(temp.getEmail().equals(email)) {
				database.remove(i);
				return "Student deleted successfully";
			}
		}
		return "ERROR: Student with email:" + email + "not found";
	}
	
}
