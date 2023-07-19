package com.chatapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.payloads.SenderRequest;

@RestController
public class ChatControllers {

	@Autowired
	private SimpMessagingTemplate messageTemplate;
	
	
	@MessageMapping("/chat")
	public void chatHandle(@Payload SenderRequest senderReq) {		
		
		System.out.println("WORKING -- --- --- ---");
		
		messageTemplate.convertAndSendToUser(senderReq.getReceiverId()+"", "/queue/message", senderReq.getMessage());
		
	}	
}
