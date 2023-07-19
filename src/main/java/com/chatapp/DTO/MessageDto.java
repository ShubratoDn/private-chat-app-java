package com.chatapp.DTO;

import java.sql.Timestamp;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class MessageDto {

	private int id;

	private int sender_id;
	
	private int receiver_id;
	
	private String content;
	
	private Timestamp timestamp;

	@Override
	public String toString() {
		return "MessageDto [id=" + id + ", sender_id=" + sender_id + ", receiver_id=" + receiver_id + ", content="
				+ content + ", timestamp=" + timestamp + "]";
	}
	
	
}
