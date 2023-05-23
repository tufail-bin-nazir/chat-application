package com.kayana.twilio.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum MessageActionEnum {

    SENT("SENT"),
    DELIVERED("DELIVERED"),
    UNDELIVERED("UNDELIVERED");

    @Getter private String value;

    public static List<String> getAllValues() {
        return List.of(MessageActionEnum.values()).stream().map(data -> data.value).collect(Collectors.toList());
    }


}
