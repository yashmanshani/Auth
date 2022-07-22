package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.LoginCredentialDTO;
import com.example.demo.DTO.Student;
import com.example.demo.Repository.DemoSQLRepository;

@Service("SQLService")
public class DemoSQLService implements DemoService{

	@Autowired
	DemoSQLRepository repo;
	
	@Override
	public String isStudentValid(LoginCredentialDTO creds) {
		String email = creds.getEmail();
		String password = creds.getPassword();
		//Select * from user where email=email and password=password;
		Optional<Student> opt = repo.findById(email);
		if(opt.isPresent()) { // Record Exists
			// check for password
			Student temp = opt.get();
			if(temp.getPassword().equals(password)) {
				return "Student valid";
			}
			else {
				return "Incorrect Password";
			}
		}
		else {
			return "Email not registered";
		}
	}

	@Override
	public int addition(int a, int b) {
		int sum = a+b;
		return sum;
	}

	@Override
	public String registerPostStudent(Student temp) {
		try {
			String email = temp.getEmail();
			Optional<Student> opt = repo.findById(email);
			if (opt.isPresent()) {
				return "Email already registerd";
			}
			repo.save(temp);
			return "Student Added Successfully";
		}
		catch(Exception e) {
			return "Error: "+e.getLocalizedMessage();
		}
	}

	@Override
	public String updateStudent(Student user) {
		// fetch from database -> name -> override
		String email = user.getEmail();
		Optional<Student> opt = repo.findById(email); //fetch from DB
		if(opt.isEmpty()) {
			return "Invalid user email";
		}
		Student temp = opt.get();
		user.setName(temp.getName());
		try {
			repo.save(user);
			return "Student Updated Successfully";
		}
		catch(Exception e) {
			return "Error: "+e.getLocalizedMessage();
		}
	}

	@Override
	public List<Student> getalluser() {
		List<Student> temp = (List<Student>) repo.findAll();
		return temp;
	}

	@Override
	public Student getStudent(String e) {
		Optional<Student> opt = repo.findById(e);
		if(opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	@Override
	public String deleteuser(String e) {
		Optional<Student> opt = repo.findById(e);
		if (opt.isPresent()) {
			repo.deleteById(e);
			return "Deletion successful";
		}
		else {
			return "No user found to delete by email: "+e;
		}
		
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
