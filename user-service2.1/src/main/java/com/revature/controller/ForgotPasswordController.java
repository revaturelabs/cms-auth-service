package com.revature.controller;

import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.entity.User;
import com.revature.mail.JavaMailUtil;
import com.revature.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/password")

public class ForgotPasswordController {
	
	UserService userService;	
	
	@Autowired
	public ForgotPasswordController(UserService userService) {
		
		this.userService=userService;
	}
	
	
	@GetMapping(value = "/forgot")
	public String forgot()
	{
		return "<H1> You has forgotten your password, we will send you an email to recover it :) </H1>";
	}
	
	
	//Here we send an email with the reset link
	@PostMapping(value = "/forgot")
	public boolean forgotPasswordEmail(@RequestBody User user) throws MessagingException {
		
		//lookup user in the database by email
		User userEmail = userService.getUserByEmail(user);
		
		if (userEmail == null) {
			
			return false;
		} else {

			//Generate random 36-character string token for reset password
			User password = new User();
			userEmail.setPassword((UUID.randomUUID().toString()));
			password = userEmail;
			
			if (JavaMailUtil.sendMail(password)) {
				
				//Encrypt new password
				BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
				//set new password
				userEmail.setPassword(bc.encode(password.getPassword()));
				
				//Save token to the database
				userService.createUser(userEmail);
			
				
			}
			
			return true;
		}
		
		
		
	}
	
	
	//Here we authenticate the link with the reset token, if true will show the reset password view
	@GetMapping(value = "/reset")
	public String resetPasswordView(Model model, @RequestParam("token") String token) {
		
		//lookup for token
		User userToken = userService.getUserByToken(token);
		//create the session attribute
		model.addAttribute("userToken",userToken);
		
		if (userToken == null) {
			
			return "Oops!  This is an invalid password reset link.";
		} else {
			
			return "Here is the view to reset you password";
		}		
		
	}
	
	 
	//Process the reset password POST
	@PostMapping(value = "/reset")
	public String setNewPassword(@ModelAttribute("userToken") User userToken, @RequestBody User passwordToken) {
		
		//Find the user associated with the reset token
		User user = userService.getUserByToken(passwordToken.getResetToken());
		
		//This should always be non-null but we check just in case
		if (user == null) {
			return "Oops!  This is an invalid password reset link.";
		} else {

			System.out.println("Changing password");
			//Encrypt new password 
			BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
			//set new password
			user.setPassword(bc.encode(passwordToken.getPassword()));
			
			
			//Set the reset token to null so it cannot be used again
			user.setResetToken(null);
			//save the new information
			userService.createUser(user); 	
			
			return "Password reseted successfully";
		}
		
	}

}






