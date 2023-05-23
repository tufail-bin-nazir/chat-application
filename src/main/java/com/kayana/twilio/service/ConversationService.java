package com.kayana.twilio.service;

import com.kayana.twilio.bean.ConversationResponseBean;
import com.kayana.twilio.bean.MessageResponseBean;
import com.kayana.twilio.entities.ChatConversation;
import com.kayana.twilio.entities.ChatMessage;
import com.kayana.twilio.enums.MessageActionEnum;
import com.kayana.twilio.repository.IConversationRepository;
import com.kayana.twilio.repository.IMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversationService {

    @Autowired
    IConversationRepository conversationRepository;

    @Autowired
    IMessageRepository messageRepository;

    public List<ConversationResponseBean> retrieveConversation(String user){
        List<ConversationResponseBean>  chatConversations
                = conversationRepository.findByFromUserOrToUser(user , user).stream().map(chat -> {
            ConversationResponseBean conversationResponseBean = new ConversationResponseBean();
            conversationResponseBean.setConversationName(chat.getConversationName());
            conversationResponseBean.setConversationId(String.valueOf(chat.getId()));
            return  conversationResponseBean;
        }).collect(Collectors.toList());


        chatConversations.stream().forEach(chat ->{
            List<ChatMessage> chatMessages =
                    messageRepository.findChatMessageByConversationId(chat.getConversationId());
            chatMessages.stream().forEach(chatMessage -> {
                MessageResponseBean messageResponseBean = new MessageResponseBean();
                messageResponseBean.setBody(chatMessage.getMessageBody());
                messageResponseBean.setFrom(chatMessage.getMessageFrom());
                messageResponseBean.setTo(chatMessage.getMessageTo());
                messageResponseBean.setStatus(chatMessage.getStatus());
                messageResponseBean.setDate(chatMessage.getCreatedDate());

                if(chatMessage.getMessageFrom().equals(user)){
                    chat.getMessageResponseBeans().add(messageResponseBean);

                }
                else if(chatMessage.getMessageTo().equals(user) &&
                        chatMessage.getStatus().equals(MessageActionEnum.DELIVERED.getValue())){
                    chat.getMessageResponseBeans().add(messageResponseBean);

                }


            });

        });

        return  chatConversations;
    }
}
