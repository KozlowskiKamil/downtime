package com.jabil.downtime.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationMessage {
    private String recipientToken;
    private String computerName;
    private String failureName;
//    private String image;
//    private Map<String, String> data;

//    public NotificationMessage(String recipientToken, String title, String body) {
//        this.recipientToken = recipientToken;
//        this.title = title;
//        this.body = body;
//    }
}
