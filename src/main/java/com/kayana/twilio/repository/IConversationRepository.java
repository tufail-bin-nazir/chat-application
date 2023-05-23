package com.kayana.twilio.repository;

import com.kayana.twilio.entities.ChatConversation;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface IConversationRepository extends JpaRepository<ChatConversation, Long> {


    Optional<ChatConversation> findByConversationName(String name);
    List<ChatConversation> findByFromUserOrToUser(String user, String user1);

}
