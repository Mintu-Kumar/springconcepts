package com.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	@RequestMapping("/")
	
	public String LoginPage()
	{
		return "loginnnn";
	}
}
