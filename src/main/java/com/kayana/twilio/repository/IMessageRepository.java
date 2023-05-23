package com.kayana.twilio.repository;

import com.kayana.twilio.entities.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface IMessageRepository extends JpaRepository<ChatMessage, Long> {

    public List<ChatMessage> findChatMessageByMessageToAndStatusIn(String to, List<String> status);

    public List<ChatMessage> findChatMessageByConversationId(String id);


}
