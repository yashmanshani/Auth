package com.example.demo.Service;

import java.util.List;

import com.example.demo.DTO.LoginCredentialDTO;
import com.example.demo.DTO.Student;

public interface DemoService {

	public String isStudentValid(LoginCredentialDTO creds);
	public int addition(int a, int b);
	public String registerPostStudent(Student temp);
	public String updateStudent(Student user);
	public List<Student> getalluser();
	public Student getStudent(String e);
	public String deleteuser(String e);
	public String addGetStudent(String n, String e, String p, String g, int a);
	
}
