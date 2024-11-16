package com.smart.services;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.smart.entities.GoogleWorkspaceUser;

public interface UserService 

{
	
	public String createUser(GoogleWorkspaceUser newUser) throws GeneralSecurityException, IOException;
	
	public String createUserOnminiOrangeServer(GoogleWorkspaceUser user);

}
