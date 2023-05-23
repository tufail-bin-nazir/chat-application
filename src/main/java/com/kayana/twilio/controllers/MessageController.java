package com.kayana.twilio.controllers;


import com.kayana.twilio.bean.MessageRequestBean;
import com.kayana.twilio.entities.ChatMessage;
import com.kayana.twilio.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private MessagingService messagingService;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, MessageRequestBean message) {
        System.out.println("handling send message: " + message + " to: " + to);
         messagingService.process(message);
  }



}
