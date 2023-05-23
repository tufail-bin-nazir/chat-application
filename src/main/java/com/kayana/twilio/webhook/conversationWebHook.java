package com.kayana.twilio.webhook;

import com.kayana.twilio.bean.MessageRequestBean;
import com.kayana.twilio.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/chat/webhook")
public class conversationWebHook {

    @Autowired
    private MessagingService messagingService;

    @GetMapping(value = "/message")
    public void addItemFilter(String webhook){

        System.out.println(webhook);
    }
}
