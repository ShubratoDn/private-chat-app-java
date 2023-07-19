package com.chatapp.helpers;

import org.springframework.stereotype.Component;

import com.chatapp.DTO.MessageDto;
import com.chatapp.DTO.UserDto;
import com.chatapp.entities.Message;
import com.chatapp.entities.User;

@Component
public class DtoConverter {

	public User userDto2User(UserDto userDto){
		User user = new User();		
		user.setId(userDto.getId());
		user.setImage(userDto.getImage());
		user.setJoinDate(userDto.getJoinDate());
		user.setUsername(userDto.getUsername());
		user.setPassword(userDto.getPassword());
		
		return user;
	}
	
	
	public UserDto user2UserDto(User user) {
		UserDto userDto = new UserDto();		
		userDto.setId(user.getId());
		userDto.setImage(user.getImage());
		userDto.setJoinDate(user.getJoinDate());
		userDto.setUsername(user.getUsername());
//		userDto.setPassword(user.getPassword());
		
		return userDto;
	}
	
	
	
	public Message messageDto2Message(MessageDto msgDto) {
		
		User sender =  new User();
		sender.setId(msgDto.getSender_id());
		
		User receiver = new User();
		receiver.setId(msgDto.getReceiver_id());
		
		Message msg = new Message();
		msg.setId(msgDto.getId());
		msg.setSender(sender);
		msg.setReceiver(receiver);
		msg.setContent(msgDto.getContent());
		msg.setTimestamp(msgDto.getTimestamp());
		return msg;
		
	}
	
}
