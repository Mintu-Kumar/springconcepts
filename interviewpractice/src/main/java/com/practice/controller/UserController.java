package com.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.entities.User;
import com.practice.service.userService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private userService service;
	
	@PostMapping("/create")
	public String userCreate()
	{
		User user =  new User();
		user.setUserId(1234);
		user.setUserName("Mintu");
		
		service.save(user);
		return "usercreated";
	}

}
