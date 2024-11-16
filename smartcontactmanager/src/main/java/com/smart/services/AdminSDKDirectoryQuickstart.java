package com.smart.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.directory.Directory;
import com.google.api.services.directory.DirectoryScopes;
import com.google.api.services.directory.model.User;
import com.google.api.services.directory.model.UserName;
import com.google.api.services.directory.model.Users;
import com.google.gson.Gson;
import com.smart.entities.GoogleWorkspaceUser;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
public class AdminSDKDirectoryQuickstart {

    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "https://www.googleapis.com/auth/admin.directory.user";
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /**
     * Directory to store authorization tokens for this application.
     */
    private static final String TOKENS_DIRECTORY_PATH = "user_token.json";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES =
            Collections.singletonList(DirectoryScopes.ADMIN_DIRECTORY_USER);

    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = AdminSDKDirectoryQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            System.out.println("file not found");
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        System.out.println("file found");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        System.out.println("credential flow");
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        System.out.println("receiver");
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public String createWorkspaceUser(GoogleWorkspaceUser wsuser) throws IOException, GeneralSecurityException {

        // Build a new authorized API client service.
        System.out.println("createWorkspaceUser");
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Directory service =
                new Directory.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(APPLICATION_NAME)
                        .build();

        System.out.println("HTTP_TRANSPORT");
        // Print the first 10 users in the domain.


       // String user1 = "{\"name\":{\"familyName\":\"Kalpesh\",\"givenName\":\"Kalepesh Hiran\"}," +
          //      "\"password\":\"Passw0rd.\",\"primaryEmail\":\"kalpesh@miniorange.in\",\"changePasswordAtNextLogin\":True}";

        //System.out.println(user1);
		
		  User new_user = new User(); 
		  UserName userName = new UserName();
		  userName.setFamilyName(wsuser.getLastName());
		  userName.setGivenName(wsuser.getFirstName()); new_user.setName(userName);
		  new_user.setPhones(wsuser.getPhone());
		  new_user.setPassword("Passw0rd.");
		  new_user.setPrimaryEmail(wsuser.getEmail());
		  new_user.setChangePasswordAtNextLogin(true);
		  
		  try { 
			  User user2 = service.users().insert(new_user).execute();
			  JSONObject jsonObject = new JSONObject(user2);
			  System.out.println(user2); 
			  System.out.println("EmailID="+jsonObject.getString("primaryEmail"));
			  if(wsuser.getEmail().equals(jsonObject.getString("primaryEmail")))
				  return "success";
		      
			 
			  return "error";
		  } catch (Exception e) {
			  System.out.println("getting exceptrion");
			  System.out.println("Exception "+ e.getMessage());
			  try {
			        ObjectMapper objectMapper = new ObjectMapper();
			        JsonNode errorJsonNode = objectMapper.readTree(e.getMessage());
			        
			       return errorJsonNode.toString();
			    } catch (Exception parseException) {
			        parseException.printStackTrace();
			    }
		  }
		 
			/*
			 * Users result = service.users().list() .setCustomer("my_customer")
			 * .setMaxResults(10) .setOrderBy("email") .execute();
			 * System.out.println("getting user list"); List<User> users =
			 * result.getUsers(); System.out.println("got user"); if (users == null ||
			 * users.size() == 0) { System.out.println("No users found."); } else {
			 * System.out.println("Users:"); for (User user : users) {
			 * System.out.println(user.getName().getFullName()); } }
			 */
		  return "";
    }
}

