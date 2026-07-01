package org.example.springtheory.ch05.ex_5_4.mailSendSystem;

public class SmsNotificationSender implements NotificationSender {
    @Override
    public void send(String to, String message) {
        System.out.printf("[SMS] to=%s : %s%n", to, message);
    }
}
