package com.jwt.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jwt.model.User;

@Service
public class UserService {
	
	private List<User> store =  new ArrayList<>();

	public UserService() {
		store.add(new User(UUID.randomUUID().toString(),"Mintu","mintu@gmail.com"));
		store.add(new User(UUID.randomUUID().toString(),"Chintu","chintu@gmail.com"));
		store.add(new User(UUID.randomUUID().toString(),"Tinku","Tinku@gmail.com"));
		store.add(new User(UUID.randomUUID().toString(),"Rinku","rinku@gmail.com"));
		
	}
	
	public List<User> getUser()
	{
		return this.store;
	}
	
	
	
	

}
