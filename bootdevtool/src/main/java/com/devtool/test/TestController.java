package com.devtool.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	
	
	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		
		int a=27;
		int b=27;
		
		return "This is for testing purpose, the sum is " + (a+b);
	}

}
