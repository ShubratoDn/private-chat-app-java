package com.chatapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chatapp.entities.Message;
import com.chatapp.entities.User;

public interface MessageRepo extends JpaRepository<Message, Integer> {

	List<Object> findDistinctReceiverBySender(User sender);	
	
	 @Query("SELECT DISTINCT chatStarted\r\n"
	 		+ "FROM (\r\n"
	 		+ "    SELECT m.receiver.id AS chatStarted FROM Message m WHERE m.sender.id = :senderId\r\n"
	 		+ "    UNION\r\n"
	 		+ "    SELECT m.sender.id AS chatStarted FROM Message m WHERE m.receiver.id = :senderId\r\n"
	 		+ ") AS combined_chats")
	 List<Integer> findDistinctReceiverIdsBySender(int senderId);

//	 SELECT * FROM `message` WHERE sender_id IN (2,4) and receiver_id IN (2,4)
//	 List<Message> findBySenderAndReceiver(List<User> senders, List<User>receivers);
	
	 List<Message> findBySender_IdInAndReceiver_IdInOrderByTimestampAsc(List<Integer> senderIds, List<Integer> receiverIds);	 

	 
//	 @Query("SELECT m FROM message m WHERE m.receiverId IN (:IDs) AND m.senderId IN (:IDs) ORDER BY m.timestamp DESC LIMIT 1")
//	 Optional<Message> findLastMessageForReceiverOrSender(@Param("IDs") List<Integer> ids);
	 Message findFirstByReceiverIdInAndSenderIdInOrderByTimestampDesc(List<Integer> receiverIds, List<Integer> senderIds);
	 
}
