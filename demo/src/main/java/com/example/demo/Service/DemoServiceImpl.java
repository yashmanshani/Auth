package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.LoginCredentialDTO;
import com.example.demo.DTO.Student;
import com.example.demo.Repository.DemoRepository;

@Service("ListService")
public class DemoServiceImpl implements DemoService{
	
	@Autowired
	DemoRepository repo;
	
	@Override
	public String isStudentValid(LoginCredentialDTO creds) {
		if (repo.valid(creds.getEmail(), creds.getPassword()) == true) {
			return "YES VALID USER";
		}
		else {
			return "NOT VALID USER";
		}
	}
	
	@Override
	public int addition(int a, int b) {
		int sum = a+b;
		return sum;
	}
	
	@Override
	public String registerPostStudent(Student temp) {
		// check if email is unique
		String email = temp.getEmail();
		Student u = repo.getStudent(email);
		if(u == null) { 
			// we can register new user
			return repo.adduser(temp);
		}
		else {
			return "Email Id already registered";
		}
	}
	@Override
	public String updateStudent(Student user) {
		return repo.updateStudent(user);
	}
	@Override
	public List<Student> getalluser() {
		return repo.getalluser();
	}
	@Override
	public Student getStudent(String e) {
		return repo.getStudent(e);
	}
	@Override
	public String deleteuser(String e) {
		return repo.deleteuser(e);
	}
	@Override
	public String addGetStudent(String n, String e, String p, String g, int a) {
		Student object = new Student();
		object.setName(n);
		object.setEmail(e);
		object.setPassword(p);
		object.setGender(g);
		object.setAge(a);
		return registerPostStudent(object);
	}

}
