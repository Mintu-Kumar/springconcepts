package com.practice.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;





@Controller
public class MyController {
	
	
	@RequestMapping(value="/about", method= RequestMethod.GET)
	public String aboutPage(Model model) {
		
		model.addAttribute("name","Mintu");
		model.addAttribute("datatime","12:30");
		System.out.println("This is under aboutPage method");
		return "about";
	}
	
	@GetMapping("/iterate-loop")
	public String ItrateLoop(Model model) {
		
		List<String> lists = List.of("Mintu","Chintu","Tinku","Rinku");
		
		model.addAttribute("names", lists);
		return "iterate";
	}
	
	@GetMapping("/condition")
	public String conditionHandler(Model model)
	{
		
		model.addAttribute("isActive",false);
		model.addAttribute("gender","M");
		return "condition";
		
	}

}
