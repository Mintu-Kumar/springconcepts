package com.practice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "userinfo")
public class User {
	
	@Id
	private int userId;
	private String userName;
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(int userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	

}
