package com.kayana.twilio.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
@Entity
@Data
@NoArgsConstructor

public class ChatMessage {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String conversationId;

    private  String messageFrom;

    private String messageTo;

    private String messageBody;

    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdDate;

    public ChatMessage(String conversationId, String messageFrom, String messageTo, String messageBody, String status, Calendar createdDate) {
        this.conversationId = conversationId;
        this.messageFrom = messageFrom;
        this.messageTo = messageTo;
        this.messageBody = messageBody;
        this.status = status;
        this.createdDate = createdDate;
    }
}
