package com.chatapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatapp.DTO.UserDto;
import com.chatapp.entities.Message;
import com.chatapp.repositories.MessageRepo;

@Service
public class MessageServices {

	@Autowired
	private MessageRepo msgRepo;
	
	@Autowired
	private UserServices userService;
	
	
//	add new messsage
	public boolean addMessage(Message msg) {
		
		Message save = msgRepo.save(msg);	
		if(save == null) {
			return false;
		}
		return true;
	}
	
	
//	Chat started with them
	public List<UserDto> chatStarted(UserDto user) throws Exception{
		
		List<Integer> findDistinctReceiverIdsBySender = msgRepo.findDistinctReceiverIdsBySender(user.getId());		
		
		List<UserDto> chatStarted = new ArrayList<>();
		
		for(int userId : findDistinctReceiverIdsBySender) {
			UserDto receiver = userService.getUserById(userId);
			chatStarted.add(receiver);
		}
		
		return chatStarted;
	}

	
	//get messages for Two users
	public List<Message> getTwoUsersMessage(int senderId, int receiverId){
		
		List<Integer> idConbination = List.of(senderId, receiverId);
		
		List<Message> conversation = msgRepo.findBySender_IdInAndReceiver_IdInOrderByTimestampAsc(idConbination, idConbination);		
		
		return conversation;
	}
	
}
