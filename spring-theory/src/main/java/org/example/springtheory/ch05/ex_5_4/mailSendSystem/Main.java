package org.example.springtheory.ch05.ex_5_4.mailSendSystem;

public class Main {
    public static void main(String[] args) {
//        NotificationSender senderEmail = new EmailNotificationSender();
//        NotificationSender senderSMS = new SmsNotificationSender();
//        NotificationSender senderKakao = new KakaoNotificationSender();
//        senderEmail.send("you", "aa");
//        senderSMS.send("you", "aa");
//        senderKakao.send("you", "aa");
//
//        NotificationSender senderDelegate = new LoggingNotificationSender(new FlakyEmailSender());
//
//        for(int i = 0; i < 3; i++) senderDelegate.send("test1", "test2");



        NotificationSender sender =
                new TimingNotificationSender(       // ③ 가장 바깥: 전체 소요 시간 측정
                        new LoggingNotificationSender(  // ② 로그 남기고
                                new RetryNotificationSender(// ① 실패하면 재시도하며
                                        new FlakyEmailSender())));// (실제 발송 대상)

        new NotificationService(sender).notifyUser("user@test.com", "안녕하세요");
    }
}
