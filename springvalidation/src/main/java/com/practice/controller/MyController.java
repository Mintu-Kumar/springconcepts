package com.practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.practice.entities.LoginData;

import jakarta.validation.Valid;

@Controller
public class MyController {

	@GetMapping("/form")
	public String openLoginForm(Model model) {
		model.addAttribute("loginData", new LoginData());
		return "form";
	}
	
	@PostMapping("/process")
	public String validateLoginFromData(@Valid @ModelAttribute("loginData") LoginData loginData, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			System.out.println(bindingResult);
			return "form";
		}
		System.out.println(loginData);
		return "success";
	}
}
