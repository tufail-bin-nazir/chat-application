package com.kayana.twilio.bean;

import lombok.Data;

@Data
public class MessageRequestBean {

    private String conversationName;

    private String conversationId;

    private String from;

    private String to;

    private String body;
}
