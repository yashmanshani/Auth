package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // don't search for the view files(html, jsp)
public class DemoController {
	
	// concept -> dependency injection
	@Autowired // connects the required object
	DemoRepository repo; //loose coupling
	//DemoRepository repo = new DemoRepository(); // tight coupling
	
	@GetMapping("/hello")
	//@ResponseBody // don't search for the view files just return the actual body
	public String sayHello() {
		return "Hello World";
	}
	// how to check  valid user through link
//	@GetMapping("/userlogin")
//	public String isUserValid(@RequestParam(name = "username") String id, @RequestParam(name = "password") String password) {
//		for (int i=0; i<database.size(); i++) { // for loop in the database
//			User temp = database.get(i); 
//			if (id.equals(temp.getEmail())) {
//				if (password.equals(temp.getPassword())) {
//					return "Yes valid user";
//				}
//			}
//		}
//		return "Not valid";
//	}
	
	//@ReuestParam - > http://localhost:8080/add?a=15&b=16
	@GetMapping("/add")
	public int addNumber(@RequestParam(name = "a") int a, @RequestParam(name = "b") int b) {
	//int ad = a + b;
	return a+b;
	}
	
	//@path parameter-> http://localhost:8080/add/15/16
	@GetMapping("/addPath/{a}/{b}")
	public int addNumbersPath(@PathVariable(name="a") int a,@PathVariable(name = "b") int b) {
		return a+b;
	}
	
	
	//Http methods-> GET, post, put, patch, delete
		// By conventions (CRUD)
		// get -> data retrieve = /getbyrollnumber/1712213087 (read)
		// post -> add data to database (Create/add)
		// put -> update data (update)
		// delete -> delete (delete)
	
	@PostMapping("/registerUser")
	public String regisspostuser(@RequestBody User temp) {
		return repo.adduser(temp);
	}
	
	@Deprecated
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
		
		return repo.adduser(object);
	}
	
	@GetMapping("/admin")
	public List<User> admin() {
		return repo.getalluser();
	}
	
	@GetMapping("/getuserbyemail/{email}")
	public User getuserbyemail(@PathVariable(name = "email") String e) {
		return repo.getUser(e);
	}
	
	@DeleteMapping("/deleteuser/{email}")
	public String deleteuserbyemail(@PathVariable(name = "email") String e) {
		return repo.deleteuser(e);
	}
}
