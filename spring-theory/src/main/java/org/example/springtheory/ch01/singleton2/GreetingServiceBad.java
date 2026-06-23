package org.example.springtheory.ch01.singleton2;

public class GreetingServiceBad {
    private static final GreetingServiceBad instance = new GreetingServiceBad();
    private GreetingServiceBad() {}
    static GreetingServiceBad getInstance() { return instance; }

    private String name;                       // ⚠️ 모든 스레드가 공유하는 필드

    String greet(String reqName) {
        this.name = reqName;                   // 내 요청을 필드에 저장
        try { Thread.sleep(5); } catch (InterruptedException e) {} // 끼어들 틈
        return this.name;                      // 그 사이 덮어써졌을 수 있다!
    }
}
