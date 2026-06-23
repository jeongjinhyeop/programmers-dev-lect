package org.example.springtheory.ch01.singleton2;

class GreetingServiceGood {
    private static final GreetingServiceGood instance = new GreetingServiceGood();
    private GreetingServiceGood() {}
    static GreetingServiceGood getInstance() { return instance; }

    // 요청 데이터를 담는 필드가 '없다'

    String greet(String reqName) {             // reqName = 파라미터(지역) → 스레드마다 따로
        try { Thread.sleep(5); } catch (InterruptedException e) {}
        return reqName;                        // 절대 안 섞인다
    }
}
