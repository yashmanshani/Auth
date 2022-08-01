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



// GIVE MIKE 2500 rupees = > dfsgfhehheojgbrgjwfiohfijbjewtgh #For data integrity

// session H2 database => session id, valid thru, email

// {session id=11464, valid thru=12/15/2022, email=utalmign@hacm.com} => hash => nsdogdg64gw49rergesheheprkghewpmwew12ew4gwgoghogn
// secret key =>gonsgojdfbgiefbghiefhefijo
// {session id=11464, valid thru=12/15/2022, email=utalmign@hacm.com} => encode 
// base64-> dfgiwnnwowv5615srgergihbwonwosaggdsgshdfhjd   hdfbsdfibdsgonsgojdfbgiefbghiefhefijo => {session id=11464, valid thru=12/15/2030, email=utalmign@hacm.com}
// hash-> hash(dfgiwnnwowv564v5+rgergihbwonwobsdfibds)=>dsbfgsijgbdwigbdiwfbewofheww6wgrwgknbw

//dfgiwnnwowv1564v5+rgergihbwonwobsdfibds.dsbfgsijgbdwigbdiwfbewofheww6wgrwgknbw
//dfgiwnnwowv5615srgergihbwonwosaggdsgshdfhjdhdfbsdfibds.dufghdsvifgbdwgkfbwiofgbewifgbwoigbdwg

// JWT -> JSON Web token -> clone
// header = {}
// payload = {session id=11464, valid thru=12/15/2022, email=utalmighty@hacm.com} 
// base64 => sadfjhfwbigbeijbvejbnefonefobndefiobjndefkefdkbjve
// secret key => djgbdwgijobeijvvnsdgkjb
// hash = (base64+secretkey) =>fahfbkjbkbqwff65ewfewfjnbewfijnwfogh387ref 
// base64.hash => sadfjhfwbigbeijbvejbnefonefobndefiobjndefkefdkbjve.fahfbkjbkbqwff65ewfewfjnbewfijnwfogh387ref

// client side - {session id=11464, valid thru=12/15/2032, email=utalmighty@hacm.com}
// base64 - dgkngpsnmgsg25sggljrgr6gerwg6
// gussedsectre key - gwgjgbksbgs
// SHA256 - hash(base64+guessedsecretkey) -> hfhbvdsvdsvbdibdsfjbvdsfkhbdvaffhfhabvfav
// dgkngpsnmgsg25sggljrgr6gerwg6.hfhbvdsvdsvbdibdsfjbvdsfkhbdvaffhfhabvfav

// {session id=11464, valid thru=12/15/2032, email=utalmighty@hacm.com} 
// encode64 = dgkngpsnmgsg25sggljrgr6gerwg6
// hash(encode64+secretkey) => fmnbgdsbgiwbgiwbgeiwgbdwigbwifgjbewifgbewiogb

// APPLICATION FLOW
// create- email, name ,password <verify>
// login - email/pass, session
// update basic info -> session through
// change password -> session through
// forgot password -> email through

// RestTemplate -> to make rest calls on other services.




