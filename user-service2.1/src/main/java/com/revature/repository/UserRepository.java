package com.revature.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.revature.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	 /**
	  * 
	  * @param email
	  * @return A full user object is returned in connection with the email. If no user is found with the parameter email
	  * 		associated to it the method will return null
	  */
	 @Query("select u from User u where u.email = :email")
	    User findByEmailReturnStream(String email);
	 
	 @Query("select u from User u where u.resetToken = :resetToken")
	    User findByTokenReturnStream(String resetToken);

}
