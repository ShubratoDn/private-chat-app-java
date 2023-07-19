package com.chatapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatapp.DTO.UserDto;
import com.chatapp.entities.User;
import com.chatapp.helpers.DtoConverter;
import com.chatapp.repositories.UserRepo;

@Component
public class UserServices {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private DtoConverter converter;
	
	
//	adding new User
	public boolean addNewUser(UserDto userDto) {
		User user = converter.userDto2User(userDto);
		User save = userRepo.save(user);
		if(save == null) {
			return false;
		}
		return true;
	}
	
	
	// Get User By username
	public UserDto getUserByUserName(String username) {		
		User user = userRepo.findByUsername(username);
		if(user == null) {
			return null;
		}		
		UserDto userDto = converter.user2UserDto(user);		
		return userDto;
	}
	
	
	// Get User By username
	public UserDto getUserByUserNameForLogin(String username) {		
		User user = userRepo.findByUsername(username);
		if(user == null) {
			return null;
		}		
		UserDto userDto = converter.user2UserDto(user);
		userDto.setPassword(user.getPassword());
		return userDto;
	}
	
	
	//get user by user id
	public UserDto getUserById(int id) throws Exception {	
		
		User user = userRepo.findById(id).orElseThrow( ()-> new Exception("User not Found"));
		 
		if(user == null) {
			return null;
		}		
		UserDto userDto = converter.user2UserDto(user);
		return userDto;
	}
	
	
	
	//for searching
	
	public List<UserDto> searchUser(String text) {
		
		List<UserDto> searchResult = new ArrayList<>();
		
		List<User> findByUsernameContaining = userRepo.findByUsernameContaining(text);
		
		for(User user: findByUsernameContaining) {
			UserDto user2UserDto = converter.user2UserDto(user);
			searchResult.add(user2UserDto);
		}
		return searchResult;
		
	}
	
}
