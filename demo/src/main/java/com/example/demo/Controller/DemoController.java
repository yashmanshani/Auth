package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.LoginCredentialDTO;
import com.example.demo.DTO.Student;
import com.example.demo.Service.DemoService;

@RestController // don't search for the view files(html, jsp)
public class DemoController {
	
	//HTTP methods-> GET, post, put, patch, delete
			// By conventions (CRUD)
			// get -> data retrieve = /getByRollNumber/1712213087 (read)
			// post -> add data to database (Create/add)
			// put -> update data (update)
			// delete -> delete (delete)
	
	// concept -> dependency injection
	@Autowired // connects the required object
	@Qualifier("SQLService")
	DemoService service; //loose coupling
	//DemoService service = new DemoService(); // tight coupling
	
	@GetMapping("/hello")
	//@ResponseBody // don't search for the view files just return the actual body
	public String sayHello() {
		return "Hello World";
	}
	
	// how to check  valid user through POST
	@PostMapping("/userlogin")
	public String isStudentValid(@RequestBody LoginCredentialDTO creds) {
		return service.isStudentValid(creds);
	}
	
	//@RequestParam - > http://localhost:8080/add?a=15&b=16
	@GetMapping("/add")
	public int addNumber(@RequestParam(name = "a") int a, @RequestParam(name = "b") int b) {
		return service.addition(a, b);
	}
	
	//@path parameter-> http://localhost:8080/add/15/16
	@GetMapping("/addPath/{a}/{b}")
	public int addNumbersPath(@PathVariable(name="a") int a,@PathVariable(name = "b") int b) {
		return service.addition(a, b);
	}
	
	//update
	@PutMapping("/update")
	public String update(@RequestBody Student user) {
		return service.updateStudent(user);
	}
	
	@PostMapping("/registerStudent")
	public String regisspostuser(@RequestBody Student temp) {
		return service.registerPostStudent(temp);
	}
	
	@Deprecated
	@GetMapping("/register/{name}/{email}/{password}/{gender}/{age}")
	public String registerUSer(@PathVariable(name = "name") String n,
			@PathVariable(name = "email") String e, @PathVariable(name = "password") String p, 
			@PathVariable(name = "gender") String g, @PathVariable(name = "age") int a) {
		return service.addGetStudent(n, e, p, g, a);
	}
	
	@GetMapping("/admin")
	public List<Student> admin() {
		return service.getalluser();
	}
	
	@GetMapping("/getuserbyemail/{email}")
	public Student getuserbyemail(@PathVariable(name = "email") String e) {
		return service.getStudent(e);
	}
	
	@DeleteMapping("/deleteuser/{email}")
	public String deleteuserbyemail(@PathVariable(name = "email") String e) {
		return service.deleteuser(e);
	}
}
