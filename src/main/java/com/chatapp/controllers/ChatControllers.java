package com.chatapp.controllers;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.DTO.UserDto;
import com.chatapp.entities.Message;
import com.chatapp.entities.User;
import com.chatapp.helpers.DtoConverter;
import com.chatapp.payloads.SenderRequest;
import com.chatapp.services.MessageServices;
import com.chatapp.services.UserServices;

@RestController
public class ChatControllers {

	@Autowired
	private SimpMessagingTemplate messageTemplate;
	
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private MessageServices msgServices;
	
	@MessageMapping("/chat")
	public void chatHandle(@Payload SenderRequest senderReq) throws Exception {		
		
		System.out.println("WORKING -- --- --- ---");
		System.out.println(senderReq);
		
		
		int sender_id = Integer.parseInt(senderReq.getSenderId());
		int receiver_id = Integer.parseInt(senderReq.getReceiverId());
		
		UserDto senderDto = userServices.getUserById(sender_id);
		UserDto receiverDto = userServices.getUserById(receiver_id);
		
		DtoConverter converter = new DtoConverter();
		User sender = converter.userDto2User(senderDto);
		User receiver = converter.userDto2User(receiverDto);
		
		
		long currentTimeMillis = System.currentTimeMillis();
        Timestamp currentTimestamp = new Timestamp(currentTimeMillis);		
		
		Message message = new Message();
		message.setContent(senderReq.getMessage());
		message.setSender(sender);
		message.setReceiver(receiver);
		message.setTimestamp(currentTimestamp);
		
		boolean flag = addNewMessage(message);
		
		if(!flag) {
			System.out.println("FAILED TO STORE DATA");
			return;
		}
		
		messageTemplate.convertAndSendToUser(senderReq.getReceiverId()+"", "/queue/message", message);
		
	}	
	
	
	
//	add new message
	private boolean addNewMessage(Message msg) {
		long currentTimeMillis = System.currentTimeMillis();
		Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
		// setting time
		msg.setTimestamp(currentTimestamp);

		if (msg.getContent() != null) {
			boolean addMessageSignal = msgServices.addMessage(msg);
			return addMessageSignal;
		}
		return false;
	}
}

