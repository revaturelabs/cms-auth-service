	package com.revature.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dto.UserDTO;
import com.revature.entity.User;
import com.revature.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class UserController {
	
	UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		
		this.userService=userService;
	}
	
	
	@GetMapping("test")
	public String hello() {
		
		return "hello";
	}
	
	@PostMapping(value = "/user")
	public ResponseEntity<UserDTO> addCustomer(@RequestBody UserDTO userdto)
	{
		UserDTO createdUser = userService.addNewUser(userdto);
		System.out.println(createdUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(userdto);
	}
	
	@PostMapping(value="/login")
	public User login(@RequestBody User user) {
		User removePassword = userService.Authenticate(user);
		User temp = new User(removePassword.getId(),removePassword.getEmail(),removePassword.getFname(),removePassword.getLname(),removePassword.getCreatedDate(),removePassword.getResetToken(),removePassword.getRole());
		
		return temp;	

	}

}
