package org.example.springtheory.ch05.ex_5_4.mailSendSystem;

public class NotificationService  {
    private final NotificationSender sender;

    public NotificationService(NotificationSender sender){
        this.sender = sender;
    }

    public void notifyUser(String email, String message){
        sender.send(email, message);
    }


}
