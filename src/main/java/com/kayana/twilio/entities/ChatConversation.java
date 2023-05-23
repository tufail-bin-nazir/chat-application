package com.kayana.twilio.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Data
public class ChatConversation {

    @Id()
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String conversationName;

    private String fromUser;

    private String toUser;

    @Temporal(TemporalType.TIMESTAMP)
    public Calendar conversationTimeStamp;

}
