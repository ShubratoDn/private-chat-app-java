package com.chatapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chatapp.entities.Message;
import com.chatapp.entities.User;

public interface MessageRepo extends JpaRepository<Message, Integer> {

	List<Object> findDistinctReceiverBySender(User sender);	
	
	 @Query("SELECT DISTINCT m.receiver.id FROM Message m WHERE m.sender.id = :senderId")
	 List<Integer> findDistinctReceiverIdsBySender(int senderId);

//	 SELECT * FROM `message` WHERE sender_id IN (2,4) and receiver_id IN (2,4)
//	 List<Message> findBySenderAndReceiver(List<User> senders, List<User>receivers);
	
	 List<Message> findBySender_IdInAndReceiver_IdInOrderByTimestampAsc(List<Integer> senderIds, List<Integer> receiverIds);
}
