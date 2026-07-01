package org.example.springtheory.ch05.ex_5_4.mailSendSystem;

public class RetryNotificationSender implements NotificationSender {
    private final NotificationSender delegate;

    public RetryNotificationSender(NotificationSender delegate) {
        this.delegate = delegate;
    }

    @Override
    public void send(String to, String message) {
        int maxAttempts = 3;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                delegate.send(to, message);
                return; // 성공 시 종료
            } catch (RuntimeException e) {
                System.out.printf("[RETRY] 예외 발생 -> %d번째 재시도 중... (사유: %s)%n", attempt, e.getMessage());
                if (attempt == maxAttempts) {
                    throw e; // 최종 실패 시 예외 던짐
                }
            }
        }
    }
}
