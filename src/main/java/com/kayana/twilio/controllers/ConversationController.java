package com.kayana.twilio.controllers;

import com.kayana.twilio.bean.ConversationResponseBean;
import com.kayana.twilio.entities.ChatConversation;
import com.kayana.twilio.entities.ChatMessage;
import com.kayana.twilio.service.ConversationService;
import com.kayana.twilio.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @GetMapping("/retrieve/{user}")
    public List<ConversationResponseBean> retrieveMessage(@PathVariable String user) {
        return conversationService.retrieveConversation(user);
    }
}
