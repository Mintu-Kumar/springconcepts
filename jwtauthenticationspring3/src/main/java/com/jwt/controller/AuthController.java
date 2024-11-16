package com.jwt.controller;

import javax.management.RuntimeErrorException;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.model.JwtRequest;
import com.jwt.model.JwtResponse;
import com.jwt.security.JwtHelper;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private JwtHelper helper;
	
	private Logger logger =  LoggerFactory.getLogger(AuthController.class);
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest)
	{
		System.out.println(""+jwtRequest.getEmail() + jwtRequest.getPassword());
			this.doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
		
			UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
			String token = this.helper.generateToken(userDetails);
			//JwtResponse response =  jwtResponse.setJwtToken(token).setUsername(jwtRequest.getEmail());
			JwtResponse response =  JwtResponse.builder()
					.jwtToken(token)
					.username(userDetails.getUsername()).build();
			return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	private void doAuthenticate(String email, String password) {
		 UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		 try {
			manager.authenticate(authentication);
		} catch (BadCredentialsException e) {
			throw new RuntimeException("Invalid Username and Password!!");
			// TODO: handle exception
		}
	}
	
	

}
