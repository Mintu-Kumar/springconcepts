package com.practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.dao.userRepository;
import com.practice.entities.User;

@Service
public class userServiceImpl implements userService {

	@Autowired
	private userRepository repository;
	@Override
	public void save(User user) {
		this.repository.save(user);
		
		
	}

}
