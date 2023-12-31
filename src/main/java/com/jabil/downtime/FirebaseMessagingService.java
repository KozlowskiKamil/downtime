package com.jabil.downtime;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.jabil.downtime.model.NotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirebaseMessagingService {

    @Autowired
    private FirebaseMessaging firebaseMessaging;

    public String sendNotificationByToken(NotificationMessage notificationMessage) {
        Notification notification = Notification
                .builder()
                .setTitle(notificationMessage.getComputerName())
                .setBody(notificationMessage.getFailureName())
//                .setImage(notificationMessage.getImage())
                .build();
        Message message = Message
                .builder()
                .setToken(notificationMessage.getRecipientToken())
                .setNotification(notification)
//                .putAllData(notificationMessage.getData())
                .build();
        try {
        firebaseMessaging.send(message);
        return "Succes Sending Notification";
        }catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return "Error Sending Notification";
        }
    }
}