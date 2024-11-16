package com.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
import com.app.entity.User;
@Controller
public class UserController {

	@GetMapping("/")
	public String login(Model model)
	{
		User user = new User();
		model.addAttribute("user",user);
		return "loginnnn";
	}
}
