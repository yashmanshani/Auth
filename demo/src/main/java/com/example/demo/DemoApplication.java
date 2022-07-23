package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// Rest API
// main file
// default config are applied to the project automatically/ reads config data from application file
// component scanning
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		//demoController controller = new demoController();
	}

}
// component are classes whose object are to be automatically created by spring boot and put into the containers
// this file scans for the components and add them to the containers

// Component types -> controller, service, repository
 	// controller -> handles all the endpoints
	//  | ^
	// service -> business logic
	//	| ^
	// repository/DAO(data access object) -> is used to handle database operations

// demoController, DemoRepository -> automatically object created and put into container
// beans means object
// A - B - C
// GRACEFULLY FAIL

// ORM -> Object-Relation mapping -> Hibernate, jOOQ.
// JDBC -> Java DataBase Connection  (Java <-> ORM <-> JDBC(SQL) <-> Database)

// SQL -> postgresSQL, mysql, sql-lite, (H2..)

// MVC - Model(Object to transfer data), View(html, JSP), Controller(manages endpoints)



/// authentication 


// we will User -> id, name, email, password.
// -> session key ->  use of HMAC (name, email, application-id, valid-thru)
// -> OTP generation -> verification, password changes. if someone changes password delete all the sessions..

// register, login, update, delete, verify session, generate Session....



// user authentication -> (reg, update, validate/login, delete) - 
//   |
// session service -> (user sessions are managed) // h2 database

// Send Mail -> OTP.



