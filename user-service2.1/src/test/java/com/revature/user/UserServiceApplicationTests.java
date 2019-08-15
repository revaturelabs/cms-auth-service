package com.revature.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.entity.User;
import com.revature.repository.UserRepository;
import com.revature.service.UserService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = com.revature.UserServiceApplication.class)
@SpringBootTest

public class UserServiceApplicationTests {
	
	@Autowired
	UserService uService;
	
	@Autowired
	UserRepository uR;
	
	@Test
	public void testFindById() {
			
		String test = "Jared";
		User found = uService.getUserById(101);
		
	    assertEquals(found.getFname(), test);
	}
	
	@Test
	public void testFindByEmail() {
		
		User test = new User("jhansen@gmail.com");
		User found = uService.getUserByEmail(test);
	    assertEquals(found.getEmail(), test.getEmail());
	}
	
	@Test
	public void testingFindByEmail() throws Exception {
	    // given
	    User test = new User("the@revature.com", "pass");
	    User found = uR.findByEmailReturnStream("the@revature.com");
	    assertEquals(test.getEmail(), found.getEmail());
	}
	
	@Test
	public void testingAuthenticationr() throws Exception {
		User tester = new User("the@revature.com", "pass");
		User testRetrieve = uService.Authenticate(tester);
		String expected = "the";
		assertEquals(expected, testRetrieve.getFname());
	}
	
}
