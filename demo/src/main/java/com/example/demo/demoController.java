package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demoController {
	
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello World";
	}
	// how to check  valid user through link
	@GetMapping("/userlogin")
	public String isUserValid(@RequestParam(name = "username") String id, @RequestParam(name = "password") String password) {
		String username = "Yashmanshani69";
		String pass  ="yashpass";
		if(id.equals(username) && pass.equals(password)) {
			return "YES VALID USER";
		}
		else {
			return "NOT VALID";
		}
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
}
