package com.chatapp.entities;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "sender_id", foreignKey = @ForeignKey(name = "fk_sender"))
	private User sender;
	
	
	@ManyToOne
	@JoinColumn(name = "receiver_id", foreignKey = @ForeignKey(name = "fk_receiver"))	
	private User receiver;

	
	@Column(length = 5000)
	private String content;
	
	
	private Timestamp timestamp;


	@Override
	public String toString() {
		return "Message [id=" + id + ", sender=" + sender + ", receiver=" + receiver + ", content=" + content
				+ ", timestamp=" + timestamp + "]";
	}
	
	
	
	
}
