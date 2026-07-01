package org.example.springtheory.ch05.ex_5_4.mailSendSystem;

public class KakaoNotificationSender implements NotificationSender{
    @Override
    public void send(String to, String message) {
        System.out.printf("[KAKAO] to=%s : %s%n", to, message);
    }
}
