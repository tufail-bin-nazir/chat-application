package com.kayana.twilio.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ConversationResponseBean {
    private String conversationId;
    private String conversationName;
    private List<MessageResponseBean> messageResponseBeans = new ArrayList<>();


}
