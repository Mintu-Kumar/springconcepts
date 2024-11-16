package com.smart.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.GoogleWorkspaceUser;
import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository repository;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		
		String username =  principal.getName();
		User user =  this.repository.getUserByUserName(username);
		model.addAttribute("user", user);
	}
	
	//dashboard home
	@GetMapping("/index")
	public String dashboard(Model model, Principal principal) {
		
		model.addAttribute("title","User Dashboard");
		return "normal/user_dashboard";
	}
	
	//open add from handler
	@GetMapping("/add-contact")
	public String openAddContactFrom(Model model) {
		
		model.addAttribute("title","Add Contact");
		model.addAttribute("contact", new Contact());
		return "normal/add_contact_from";
	}
	@GetMapping("/create-user")
	public String openCreateUserForm(Model model) {
		
		model.addAttribute("title","Crate User");
		model.addAttribute("wsuser",new GoogleWorkspaceUser());
		return "normal/create_user_form";
	}
	
	//processing add contact form
	@PostMapping("/process-contact")
	public String proccessContact(
				@Valid @ModelAttribute Contact contact,
				BindingResult result, 
				@RequestParam("profileImage") MultipartFile file, 
				Principal principal,
				Model model,
				HttpSession session)
	{
		if(result.hasErrors()) {
			model.addAttribute("contact",contact);
			return "normal/add_contact_from";
		}
		try {
		String name = principal.getName();
		System.out.print("name="+name);
		User user  =  repository.getUserByUserName(name);
		contact.setUser(user);
		//processing and uploading file
		if(file.isEmpty()) {
			System.out.println("File is empty");
			contact.setImage("contact.png");
		}else {
			contact.setImage(file.getOriginalFilename());
			File saveFile = new ClassPathResource("static/image").getFile();
			Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
			 Files.copy(file.getInputStream(), path  ,
	                    StandardCopyOption.REPLACE_EXISTING);
			 session.setAttribute("message", new Message("Your contact is added!! Add more", "success"));
			System.out.println("File is uploaded");
		}
		user.getContacts().add(contact);
		this.repository.save(user);
		}
		catch (Exception e) {
			session.setAttribute("message", new Message("Something went wrong !! Try again", "error"));
			System.out.println("ERROR="+e.getMessage());
		}
		
		return "normal/add_contact_from";
	}
	// show contact handler
	@GetMapping("/show-contacts/{page}")
	public String showContacts(@PathVariable("page") Integer page, Model model, Principal principal) {
		model.addAttribute("title","Show User Contacts");
		String name  = principal.getName();
		User user = this.repository.getUserByUserName(name);
		// pageable ke pass 2 chij honge ek page and dusra per page kitna contact hoga
		Pageable pageable = PageRequest.of(page, 5);
		Page<Contact> contacts = this.contactRepository.findListByUser(user.getId(),pageable);
		model.addAttribute("contacts",contacts);
		model.addAttribute("currentPage",page);
		model.addAttribute("totalPages",contacts.getTotalPages());
		return "normal/show_contact";
	}
	// showing particular contact detail
	@GetMapping("/{cId}/contact")
	public String showContactDetail(
			@PathVariable("cId") Integer cId,
			Model model, 
			Principal principal) {
		
		Optional<Contact> contactOptional = this.contactRepository.findById(cId);
		Contact contact = contactOptional.get();
		String name =  principal.getName();
		User user  =  this.repository.getUserByUserName(name);
		
		if(user.getId() == contact.getUser().getId())
		{   
			model.addAttribute("contact",contact);
			model.addAttribute("title",contact.getName());
		}
		return "normal/contact_detail";
	}
	
	//Deleting particular contact
	@GetMapping("/delete/{cId}")
	public String deleteContact(
			@PathVariable("cId") Integer cId,
			Principal principal,
			HttpSession session) {
		
		 Optional<Contact> contactOptional = this.contactRepository.findById(cId);
		 Contact contact = contactOptional.get();
		 String name =  principal.getName();
		 User user  =  this.repository.getUserByUserName(name);
		 //check 
		 if(user.getId() == contact.getUser().getId())
		    this.contactRepository.delete(contact);
		 
		 session.setAttribute("message", new Message("Contact deleted successfully", "success"));
		 return "redirect:/user/show-contacts/0";
	}
	
	
	// updating contact
	@PostMapping("/update-contact/{cId}")
	public String updateContact(@PathVariable("cId") Integer cId,Model model)
	{
		
		model.addAttribute("title","Update contact");
			Contact contact = this.contactRepository.findById(cId).get();
			model.addAttribute("contact",contact);
		return "normal/update_form";
	}
	
	
	@PostMapping("/create-workspace-user")
	public String createWorkspaceUser(@Valid @ModelAttribute GoogleWorkspaceUser wsuser,
									BindingResult result,
									Model model,
									HttpSession session) throws GeneralSecurityException, IOException
	{
		 if(result.hasErrors()) {
			 System.out.println("result="+result);
			 model.addAttribute("wsuser",wsuser);
			 return "normal/create_user_form";
		 }
		
		String workspaceStatus = userService.createUser(wsuser);
		if(workspaceStatus.equals("409"))
		{
			session.setAttribute("message", new Message("This user already exists in the workspace", "error"));
			model.addAttribute("wsuser",wsuser);
			return "normal/create_user_form";
		}else if(workspaceStatus.equals("success")) {
			
			String status = userService.createUserOnminiOrangeServer(wsuser);
			if(status.equals("success"))
			{
				session.setAttribute("message", new Message("User created successfully", "success"));
				return "normal/create_user_form";
			}else {
				System.out.println("status="+status);
				session.setAttribute("message", new Message(status, "error"));
				model.addAttribute("wsuser",wsuser);
				return "normal/create_user_form";
				
			}
		
		}
		return "normal/create_user_form";
	}
	@PostMapping("/process-update")
	public String updateHandler(@Valid @ModelAttribute Contact contact,
								BindingResult result,
								@RequestParam("profileImage") MultipartFile file,
								Model model,
								HttpSession session,
								Principal principal) {
		
		try {
			
			Contact oldContactDetail =  this.contactRepository.findById(contact.getcId()).get();
			//image
			if(!file.isEmpty())
			{
			   //delete old photo
				File deleteFile = new ClassPathResource("static/image").getFile();
				File file1 =  new File(deleteFile, oldContactDetail.getImage());
				file1.delete();
				// update new photo
				File saveFile = new ClassPathResource("static/image").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);
				contact.setImage(file.getOriginalFilename());
			}else {
				
				contact.setImage(oldContactDetail.getImage());
			}
			
			User user =  this.repository.getUserByUserName(principal.getName());
			contact.setUser(user);
			this.contactRepository.save(contact);
			session.setAttribute("message", new Message("Your contact is updated..", "success"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Contact="+ contact.getName());
		System.out.println("Contact="+ contact.getcId());
		return "redirect:/user/"+contact.getcId()+"/contact";
	}
	
	@GetMapping("/profile")
	public String yourProfile(Model model)
	{
		model.addAttribute("title","Profile page");
		return "normal/profile";
	}
	
	
}
