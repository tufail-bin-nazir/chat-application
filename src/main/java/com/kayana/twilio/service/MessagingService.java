package com.kayana.twilio.service;

import com.kayana.twilio.bean.MessageRequestBean;
import com.kayana.twilio.entities.ChatConversation;
import com.kayana.twilio.entities.ChatMessage;
import com.kayana.twilio.enums.MessageActionEnum;
import com.kayana.twilio.repository.IConversationRepository;
import com.kayana.twilio.repository.IMessageRepository;
import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.conversations.v1.Conversation;
import com.twilio.rest.conversations.v1.ParticipantConversation;
import com.twilio.rest.conversations.v1.User;
import com.twilio.rest.conversations.v1.conversation.Message;
import com.twilio.rest.conversations.v1.conversation.Participant;
import com.twilio.rest.conversations.v1.conversation.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class MessagingService {

    @Autowired
    IConversationRepository conversationRepository;

    @Autowired
    IMessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    public void process(MessageRequestBean messageRequestBean){

        MessageActionEnum messageActionEnum = null;
        List<String> onlineUsers = userService.retrieveAllOnlineUsers();
        String conversationName = "Conversation_"+ messageRequestBean.getFrom() + "_"+ messageRequestBean.getTo();
        String conversationId = "";
        Optional<ChatConversation> conversation = conversationRepository.findByConversationName(conversationName);
        if(!conversation.isPresent()){
            ChatConversation conversation1 = new ChatConversation();
            conversation1.setConversationName(conversationName);
            conversation1.setFromUser(messageRequestBean.getFrom());
            conversation1.setToUser(messageRequestBean.getTo());
            conversation1.setConversationTimeStamp(Calendar.getInstance());
            conversationRepository.save(conversation1);
            conversationId = String.valueOf(conversation1.getId());
        }else{
            conversationId = String.valueOf(conversation.get().getId());
        }

        if(onlineUsers.contains(messageRequestBean.getTo())){
            //User Is online, send message to topic
            messageActionEnum = MessageActionEnum.DELIVERED;
            simpMessagingTemplate.convertAndSend("/topic/messages/" + messageRequestBean.getTo(), messageRequestBean);

        }else{
            messageActionEnum = MessageActionEnum.UNDELIVERED;

            //change message to status to undevelived and save in db
        }
        ChatMessage message1 = new ChatMessage(
                conversationId, messageRequestBean.getFrom(), messageRequestBean.getTo(),
                messageRequestBean.getBody(), messageActionEnum.getValue(),Calendar.getInstance());
        messageRepository.save(message1);

    }



    public List<ChatMessage> retrieve(String id) {
        List<String> statusList = List.of(MessageActionEnum.DELIVERED.getValue(),MessageActionEnum.UNDELIVERED.getValue());


        List<ChatMessage> messageList
                = messageRepository.findChatMessageByMessageToAndStatusIn(id,statusList);
        return  messageList;

    }

    public void recieveAllConversation(String userIdentifier) {

        Twilio.init("ACb30d24ad3bce3fd468d2c8a6d9522365", "1f3042427369ba7a3becdfd499ceddc7");
        ResourceSet<ParticipantConversation> participantConversations =
                ParticipantConversation.reader()
                        .setIdentity(userIdentifier)
                        .limit(20)
                        .read();

        for(ParticipantConversation record : participantConversations) {
            System.out.println(record.getConversationFriendlyName());
        }
    }


    public void sendUndeliveredMessages(String onlineUser) {

        List<ChatMessage> chatMessages = this.retrieve(onlineUser);
        if(!chatMessages.isEmpty()){
            for (ChatMessage chat:chatMessages) {
                chat.setStatus(MessageActionEnum.DELIVERED.getValue());
                messageRepository.save(chat);
                MessageRequestBean messageRequestBean = new MessageRequestBean();
                messageRequestBean.setTo(chat.getMessageTo());
                messageRequestBean.setFrom(chat.getMessageFrom());
                messageRequestBean.setConversationId(chat.getConversationId());
                messageRequestBean.setBody(chat.getMessageBody());

                simpMessagingTemplate.convertAndSend("/topic/messages/" + messageRequestBean.getTo(), messageRequestBean);

            }

        }
    }
}


