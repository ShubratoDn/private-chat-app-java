package com.chatapp.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.chatapp.DTO.MessageDto;
import com.chatapp.DTO.UserDto;
import com.chatapp.entities.Message;
import com.chatapp.helpers.DtoConverter;
import com.chatapp.payloads.ApiResponse;
import com.chatapp.payloads.ChatStarted;
import com.chatapp.payloads.UserLoginInfo;
import com.chatapp.services.MessageServices;
import com.chatapp.services.UserServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class MainController {

	@Autowired
	private UserServices userService;

	@Autowired
	private MessageServices msgService;
	
	
	
	
	
	public boolean isLoggedin() {
    	// Set session without HttpSession
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
		UserDto user = (UserDto) session.getAttribute("loggedUser");
		
		if(user == null) {
			System.out.println("Not Logged In" + user);
			return false;
		}else {
			System.out.println("Loogged In" + user.getUsername());
			return true;
		}
	}	
	
	public UserDto getLoggedinUser() {
		// Set session without HttpSession
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
		UserDto user = (UserDto) session.getAttribute("loggedUser");
		
		if(user == null) {
			System.out.println("User Can not found" + user);
			return user;
		}else {
			System.out.println("Loogged In" + user);
			return user;
		}
	}

	
	
	
	
	
	
	

	
	
	
	// registering user
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserDto user) {
		
		// user name and password validation
		if (user.getUsername().length() < 4) {
			return ResponseEntity.badRequest().body(new ApiResponse("Username should be more than 4 letter", "error"));
		}

		if (user.getPassword().length() < 4) {
			return ResponseEntity.badRequest().body(new ApiResponse("Password should contain minimum four character", "error"));
		}

		UserDto userByUserName = userService.getUserByUserName(user.getUsername());
		if(userByUserName != null) {
			return ResponseEntity.badRequest().body(new ApiResponse("Username already exist", "error"));
		}

		
		Random random = new Random();
		int randomNumber = random.nextInt(5) + 1;
		user.setImage("user_image_"+randomNumber);
		user.setJoinDate(new Timestamp(System.currentTimeMillis()));
		user.setUsername(user.getUsername().toLowerCase());
		
//    	adding new User
		boolean addNewUser = userService.addNewUser(user);

		if (addNewUser) {
			return ResponseEntity.ok("Register Successfull");
		} else {
			return ResponseEntity.internalServerError().body(new ApiResponse("Unable to Register", "error"));
		}
	}
	
	
	
	
//	user login
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserLoginInfo userInfo,HttpSession session) {		
		
		UserDto userDto = userService.getUserByUserNameForLogin(userInfo.getUsername());
		if(userDto == null) {
			return ResponseEntity.badRequest().body(new ApiResponse("Username not found", "error"));
		}
		
		if(!userInfo.getPassword().equals(userDto.getPassword())) {
			System.out.println("" + userDto.getPassword());
			return ResponseEntity.badRequest().body(new ApiResponse("Password not matching!", "error"));
		}		
		
		userDto.setPassword(null);
		
		//session start kore dilam
		session.setAttribute("loggedUser", userDto);
		
		
		// Set session without HttpSession
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession sessionx = request.getSession();
		UserDto user = (UserDto) sessionx.getAttribute("loggedUser");
		
		if(user == null) {
			System.out.println("login hoynai " + user);
		}else {
			System.out.println("login hoiche " + user.getUsername());			
		}
		
		return ResponseEntity.ok(userDto);
	}
	
	//user log out
	
	
	
	
	//get USER by UserId
	@GetMapping("/getUser/{userId}")	
	public ResponseEntity<?> getUserById(@PathVariable("userId") int userId) throws Exception{
		UserDto userById = userService.getUserById(userId);
		return ResponseEntity.ok(userById);
	}
	
	
	
	//get users with whom i started chat
	@GetMapping("/chatStarted")
	public ResponseEntity<?> chatStarted(){
		UserDto loggedinUser = null;
		if(isLoggedin()) {
			loggedinUser = getLoggedinUser();
		}else {
			return ResponseEntity.badRequest().body(new ApiResponse("You are not logged in!", "error"));
		}		
		
		
		List<ChatStarted> chatStarted = new ArrayList<>();
		try {
			chatStarted = msgService.chatStarted(loggedinUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return ResponseEntity.ok(chatStarted);
	}
	
	
	
	//GET Users With chat started FINAL
	@GetMapping("/chatStarted/{userId}")
	public ResponseEntity<?> chatStartedList(@PathVariable("userId") int userId) throws Exception {
		
		UserDto loggedinUser = userService.getUserById(userId);
		
		if (loggedinUser == null) {
			return ResponseEntity.badRequest().body(new ApiResponse("You are not logged in!", "error"));
		}

		List<ChatStarted> chatStarted = new ArrayList<>();
		
		try {
			chatStarted = msgService.chatStarted(loggedinUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok(chatStarted);
	}
	
	
	
	

	
	
	//get our message for Specific User
	@GetMapping("/get-my-message/{userId}")
	public ResponseEntity<?> getMyMessagesForUser(@PathVariable("userId") String userIdString){		
		int userId = 0;
//		Validation
		try {			
			userId = Integer.parseInt(userIdString);
		}catch(Exception ex) {
			return ResponseEntity.badRequest().body(new ApiResponse("Invalid id", "error"));
		}
		
		if(userId == 0) {
			return ResponseEntity.badRequest().body(new ApiResponse("Invalid id", "error"));
		}
		
		if(!isLoggedin()) {
			return ResponseEntity.badRequest().body(new ApiResponse("User not logged in!", "error"));
		}
		
		
		//getting currently logged in person
		UserDto loggedinUser = getLoggedinUser();
		
		List<Message> conversation = msgService.getTwoUsersMessage(loggedinUser.getId(), userId);		
		
		return ResponseEntity.ok(conversation);		
	}
	
	


	
	//ETA USE KORTESI REACT APP A
	
	// get our message for Specific User
	@GetMapping("/get-our-message/{senderId}/{receiverId}")
	public ResponseEntity<?> getOurMessagesForUser(
			@PathVariable("senderId") String senderIdString,
			@PathVariable("receiverId") String receiverIdString			
			) {
		
		int senderId = 0;
		int receiverId = 0 ;

		try {
			senderId = Integer.parseInt(senderIdString);
			receiverId =  Integer.parseInt(receiverIdString);
			
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(new ApiResponse("Invalid id", "error"));
		}

		if (receiverId == 0 || senderId == 0) {
			return ResponseEntity.badRequest().body(new ApiResponse("Invalid id", "error"));
		}
	

		// getting currently logged in person
		List<Message> conversation = msgService.getTwoUsersMessage(senderId, receiverId);
		return ResponseEntity.ok(conversation);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	//seacrch user
	@GetMapping("/search/{username}")
	public ResponseEntity<?> searchResult(@PathVariable(required = false) String username){
		List<UserDto> searchUser = userService.searchUser(username);			
		return ResponseEntity.ok(searchUser);
	}
	
	
	
	
	
//	add new message API	
	@PostMapping("/addMessage")
	public ResponseEntity<?> addMsg(@RequestBody MessageDto msgDto){
		
		System.out.println("First");
		System.out.println(msgDto);
		
		DtoConverter converter = new DtoConverter();		
		Message msg = converter.messageDto2Message(msgDto);
		
		boolean addedSignal = addNewMessage(msg);		
		if(addedSignal) {
			return ResponseEntity.ok("");
		}else {
			return ResponseEntity.internalServerError().body(new ApiResponse("Message Added failed", "error"));
		}		
		
	}
	

//	add new message
	private boolean addNewMessage(Message msg){		
		long currentTimeMillis = System.currentTimeMillis();
        Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
        //setting time
		msg.setTimestamp(currentTimestamp);
		
		if(msg.getContent() != null) {			
			boolean addMessageSignal = msgService.addMessage(msg);			
			return addMessageSignal;
		}		
		return false;
	}
	
	
	
	
	
	
	
}
