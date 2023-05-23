package com.kayana.twilio.eventlistener;

import com.kayana.twilio.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class WebSocketEventListener {

    @Autowired
    MessagingService messagingService;

    public List<String> onlineUserList = new ArrayList<>();

    @EventListener
    private void handleSessionConnect(SessionConnectEvent event) {
        System.out.println(event.getMessage());
    }

    @EventListener
    private void handleSessionConnected(SessionConnectedEvent event) {
        System.out.println(event.getMessage());
    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        System.out.println(event);

    }

    @EventListener
    private void handleSessionSubscribe(SessionSubscribeEvent event){

        String onLineUser =  retrieveUserfromEvent(event);

        if(!onLineUser.isEmpty()){
            onlineUserList.add(onLineUser);
            messagingService.sendUndeliveredMessages(onLineUser);

        }
    }

    @EventListener
    private void handleSessionUnSubscribe(SessionUnsubscribeEvent event){
       String offLineUser =  retrieveUserfromEvent(event);

        if(!offLineUser.isEmpty()){
            onlineUserList.remove(offLineUser);

        }
    }

    @EventListener
    private void handleBrokerAvailabilityEvent(BrokerAvailabilityEvent event){
        System.out.println(event.isBrokerAvailable());

    }

    private String retrieveUserfromEvent(AbstractSubProtocolEvent event){
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String onlineUser = "";
        String destination  = "";


        if(event.getClass().equals(SessionSubscribeEvent.class)  && Objects.nonNull(headers.getNativeHeader("destination"))){
            destination = headers.getNativeHeader("destination").get(0);

        }
        if(event.getClass().equals(SessionUnsubscribeEvent.class) && Objects.nonNull(headers.getNativeHeader("id"))){
            destination = headers.getNativeHeader("id").get(0);

        }

        String[] destinationArray = destination.split("/");
        if(destinationArray.length == 4){
            onlineUser = destinationArray[3];
        }
        return  onlineUser;
    }

}
