package com.jpa.test;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.jpa.test.dao.UserRepository;
import com.jpa.test.entities.User;

@SpringBootApplication
public class BootjpaexampleApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BootjpaexampleApplication.class, args);
		
		UserRepository repository =  context.getBean(UserRepository.class);
		
		User user1 = new User();
		user1.setName("Amit Kumar");
		user1.setCity("Samastipur");
		user1.setStatus("I am a java Script programmer");
		
		User user2 = new User();
		user2.setName("Chintu Kumar");
		user2.setCity("Patna");
		user2.setStatus("I am a paython programmer");
		
		
		//saving single object
		 /*User user1 =  repository.save(user);*/
		List<User> users = List.of(user1,user2);
		
		Iterable<User> result =  repository.saveAll(users);
		
		result.forEach(user->{
			
			System.out.println(user);
		});
		
		
		 //System.out.println(user1);
		
	}

}
