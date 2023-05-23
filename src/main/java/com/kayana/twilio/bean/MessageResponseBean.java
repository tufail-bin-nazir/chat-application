package com.kayana.twilio.bean;

import lombok.Data;

import java.util.Calendar;

@Data
public class MessageResponseBean {
    private String body;

    private String from;

    private String to;

    private Calendar date;

    private String status;
}
