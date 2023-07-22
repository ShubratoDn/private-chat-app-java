package com.chatapp.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SenderRequest {

	private String senderId;
	private String receiverId;
	private String message;
	@Override
	public String toString() {
		return "SenderRequest [senderId=" + senderId + ", receiverId=" + receiverId + ", message=" + message + "]";
	}
	
	
	
}
