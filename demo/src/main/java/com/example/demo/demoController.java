package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demoController {
	
	List<User> database = new ArrayList<>();
	
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello World";
	}
	// how to check  valid user through link
	@GetMapping("/userlogin")
	public String isUserValid(@RequestParam(name = "username") String id, @RequestParam(name = "password") String password) {
		for (int i=0; i<database.size(); i++) { // for loop in the database
			User temp = database.get(i); 
			if (id.equals(temp.getEmail())) {
				if (password.equals(temp.getPassword())) {
					return "Yes valid user";
				}
			}
		}
		return "Not valid";
	}
	
	//@ReuestParam - > http://localhost:8080/add?a=15&b=16
	@GetMapping("/add")
	public int addNumber(@RequestParam(name = "a") int a, @RequestParam(name = "b") int b) {
	//int ad = a + b;
	return a+b;
	}
	
	//@path prameter-> http://localhost:8080/add/15/16
	@GetMapping("/addPath/{a}/{b}")
	public int addNumbersPath(@PathVariable(name="a") int a,@PathVariable(name = "b") int b) {
		return a+b;
	}
	
	@GetMapping("/register/{name}/{email}/{password}/{gender}/{age}")
	public String registerUSer(@PathVariable(name = "name") String n,
			@PathVariable(name = "email") String e, @PathVariable(name = "password") String p, 
			@PathVariable(name = "gender") String g, @PathVariable(name = "age") int a) {
		User object = new User();
		object.setName(n);
		object.setEmail(e);
		object.setPassword(p);
		object.setGender(g);
		object.setAge(a);
		
		database.add(object);
		return "User Added successfully";
	}
	
	@GetMapping("/admin")
	public List<User> admin() {
		return database;
	}
	
	//user = name
	//password
	
	//Http methods-> GET, post, put, patch, delete
	
	@GetMapping("/registerUser")
	public String regisspostuser(@RequestBody User temp) {
		database.add(temp);
		return "POST USER ADDED SUCCESSFULLY";
	}
	
}
