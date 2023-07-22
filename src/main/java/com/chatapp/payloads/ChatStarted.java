package com.chatapp.payloads;


import java.sql.Timestamp;

import com.chatapp.DTO.UserDto;
import com.chatapp.entities.Message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatStarted {
	private UserDto user;
	private Message lastMessage;
	private String messageState;
}
