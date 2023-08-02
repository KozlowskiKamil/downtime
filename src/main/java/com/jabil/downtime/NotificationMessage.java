package com.jabil.downtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationMessage {
    private String recipientToken;
    private String title;
    private String body;
    private String image;
    private Map<String, String> data;

    public NotificationMessage(String recipientToken, String title, String body, String image) {
        this.recipientToken = recipientToken;
        this.title = title;
        this.body = body;
        this.image = image;
    }
}
