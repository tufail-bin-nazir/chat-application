package com.kayana.twilio;

import com.kayana.twilio.eventlistener.WebSocketEventListener;
import com.twilio.Twilio;

import com.twilio.rest.conversations.v1.Conversation;
import com.twilio.rest.conversations.v1.Service;
import com.twilio.rest.conversations.v1.conversation.Message;
import com.twilio.rest.conversations.v1.conversation.Participant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class TwilioApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwilioApplication.class, args);


	}

	@Bean
	public WebSocketEventListener getEventListener(){
		return  new WebSocketEventListener();
	}


}
