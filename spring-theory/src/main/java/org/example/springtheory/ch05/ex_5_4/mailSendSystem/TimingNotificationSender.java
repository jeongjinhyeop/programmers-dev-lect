package org.example.springtheory.ch05.ex_5_4.mailSendSystem;

public class TimingNotificationSender implements NotificationSender{
    private NotificationSender delegate;

    public TimingNotificationSender(NotificationSender delegate){
        this.delegate = delegate;
    }
    @Override
    public void send(String to, String message) {
        long startTime = System.currentTimeMillis();
        delegate.send(to, message);
        long endTime = System.currentTimeMillis();

        // 걸린 시간 계산 (종료 시간 - 시작 시간)
        long durationMillis = endTime - startTime;        // 밀리초 단위 (1/1000초)
        double durationSeconds = durationMillis / 1000.0; // 초 단위 변환

        System.out.println("====== 성능 측정 결과 ======");
        System.out.println("걸린 시간: " + durationMillis + " ms");
        System.out.println("초 단위: " + durationSeconds + " 초");
    }
}
