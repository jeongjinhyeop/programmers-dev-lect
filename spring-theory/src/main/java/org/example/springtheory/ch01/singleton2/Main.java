package org.example.springtheory.ch01.singleton2;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static int badMismatch = 0;
    static int goodMismatch = 0;

    public static void main(String[] args) throws InterruptedException {
        int N = 30;
        /*
        *  싱글톤 상태에선 객체를 하나만 생성하니까 하나를 공유해서 쓰는데
        * 그 동작을 진행하는 중에 데이터꼬임이 발생할 수 있다
        *  그에 대한  방안은 지역변수를 사용해서 스레드별로 각자의 스택영역을 이용하여 겹치지 않게 함
        *
        * 필드를 만드는 경우 싱글턴이 깨지는 이유
        * 필드의 경우 힙영역에 생성됨
        * 객체를 생성할 때 생성자로 초기화를 하여 힙영역에 해당값을 저장
        * */
        //생성자의 역할: 힙 영역에 생긴 객체의 필드들에 값을 예쁘게 세팅(초기화)해 주는
        // 나쁜 서비스: N개 스레드 동시 실행
        Thread[] t1 = new Thread[N];
        for (int i = 0; i < N; i++) {
            final String myName = "손님" + i;
            t1[i] = new Thread(() -> {                       // 스레드가 할 일
                String r = GreetingServiceGood.getInstance().greet(myName);
                if (!r.equals(myName)) {                     // 내 이름이 안 돌아오면 = 엉킴
                    synchronized (Main.class) { badMismatch++; }
                }
            });
        }
        for (Thread t : t1) t.start();   // 동시에 출발
        for (Thread t : t1) t.join();    // 다 끝날 때까지 대기

        // 좋은 서비스도 똑같은 방식으로(t2, goodMismatch) 반복...

        System.out.println("[필드에 저장] 엉킴: " + badMismatch + " / " + N);
        System.out.println("[파라미터로]  엉킴: " + goodMismatch + " / " + N);
    }
}
