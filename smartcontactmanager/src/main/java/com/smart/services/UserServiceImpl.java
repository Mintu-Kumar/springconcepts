package com.smart.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.crypto.hash.Sha512Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.smart.entities.GoogleWorkspaceUser;
import com.smart.util.HttpAPI;
import com.smart.util.SHA512Hash;
import com.smart.util.StringUtil;

@Service
@Component
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	AdminSDKDirectoryQuickstart adminSDKDirectoryQuickstart;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createUser(GoogleWorkspaceUser wsuser) throws GeneralSecurityException, IOException {
		System.out.println("createUserImpl");
		return adminSDKDirectoryQuickstart.createWorkspaceUser(wsuser);
		
	}

	@Override
	public String createUserOnminiOrangeServer(GoogleWorkspaceUser user) {
		//getAllUser();
		
		return userCreation(user);
	
	}
	
	
	public String userCreation(GoogleWorkspaceUser user) 
	{
		String customerKey = "189833";
		String apiKey = "tREmUQ79c9iqKjZ3nDsriFXBdOegwExK";
		String Url = "https://login.xecurify.com/moas/api/admin/users/create";
		 try{
	         

	            //body
	            Map<String, Object> body = new HashMap<>();
	            body.put("customerKey",customerKey );
	            body.put("username", user.getEmail());
	            body.put("email", user.getEmail());
	            body.put("password", "Passw0rd.");
	            body.put("firstName", user.getFirstName());
	            body.put("lastName", user.getLastName());
	            body.put("phone", user.getPhone());

	            // Headers
	            Map<String, String> headers = new HashMap<>();
	            String currentTimeInMillis = String.valueOf(System.currentTimeMillis());
	            String stringToHash = customerKey + currentTimeInMillis + apiKey;
	            String hashValue = new Sha512Hash(stringToHash).toHex().toLowerCase();
	            headers.put("Customer-Key", customerKey);
	            headers.put("Timestamp",currentTimeInMillis );
	            headers.put("Authorization", hashValue);
	            headers.put("Content-Type","application/json");
	            
	            // Send POST Request
	            String response = HttpAPI.sendPOST(Url, StringUtil.mapToJson(body), headers);
	            
	            if(response.contains("SUCCESS")){
	                return "success";
	            }
	            if(response.contains("Another account is linked to this email") || response.contains("User already exists with this username")){
	                return "Account already exists with this username or email in your indentity provider";
	            }
	            else{
	                System.out.println("response="+response);
	                return "Internal Error Occured";
	            }
	        }catch (Throwable e){
	            e.printStackTrace();
	            return "An Exception Occured, Pleas try Again";
	        }
	}
	
	public void getAllUser() {
		
		String customerKey = "189833";
		String apiKey = "tREmUQ79c9iqKjZ3nDsriFXBdOegwExK";
		String Url = "https://login.xecurify.com/moas/api/admin/users/getall";
		 try{
	         

	            //body
	            Map<String, Object> body = new HashMap<>();
	            body.put("customerKey",customerKey );
	            body.put("batchSize", 500);
	            body.put("batchNo", 1);

	            // Headers
	            Map<String, String> headers = new HashMap<>();
	            String currentTimeInMillis = String.valueOf(System.currentTimeMillis());
	            String stringToHash = customerKey + currentTimeInMillis + apiKey;
	            String hashValue = new Sha512Hash(stringToHash).toHex().toLowerCase();
	            headers.put("Customer-Key", customerKey);
	            headers.put("Timestamp",currentTimeInMillis );
	            headers.put("Authorization", hashValue);
	            headers.put("Content-Type","application/json");
	            
	            // Send POST Request
	            String response = HttpAPI.sendPOST(Url, StringUtil.mapToJson(body), headers);
	            System.out.println("response="+response);
	            if(response.contains("SUCCESS")){
	               System.out.println(response);
	            }
	            if(response.contains("Another account is linked to this email") || response.contains("User already exists with this username")){
	            	System.out.println("response="+response);
	            }
	            else{
	                System.out.println("response="+response);
	              
	            }
	        }catch (Throwable e){
	            e.printStackTrace();
	            System.out.println("response="+e.getMessage());
	        }
	}
	
	

}
