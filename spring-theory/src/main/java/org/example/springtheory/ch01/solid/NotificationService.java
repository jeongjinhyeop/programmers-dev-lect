package org.example.springtheory.ch01.solid;


public class NotificationService {
    private MessageSender sender;

    public NotificationService(MessageSender messageSender){
        this.sender = messageSender;
    }

    void notifyUser(String msg) { sender.send(msg); }
}
