package com.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping("/welcome")
	public String welcome()
	{
		String text = "this is testing";
		return text;
	}

}
