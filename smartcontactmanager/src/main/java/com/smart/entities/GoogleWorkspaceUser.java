package com.smart.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class GoogleWorkspaceUser {
	
	@Id
	private int id;
	@NotBlank(message = "This field may not be empty")
	private String firstName;
	@NotBlank(message = "This field may not be empty")
	private String lastName;
	@NotBlank(message = "This field may not be empty")
	private String phone;
	@NotBlank(message = "This field may not be empty")
	private String email;
	
	public GoogleWorkspaceUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	

}
