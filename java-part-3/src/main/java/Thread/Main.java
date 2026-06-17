package Thread;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
       /* CountThread t1 = new CountThread();
        t1.start();
        new Scanner(System.in).nextLine();   // 아무 값 입력 대기
        t1.interrupt();

        CountSleepThread t2 = new CountSleepThread();
        t2.start();
        new printDash().start();
        new printBar().start();
        System.out.println("인터럽트던진다!");
        t2.interrupt();*/

        // yield를 사용해도 순서가 무조건적으로 보장되는 것은아님 순서가 보장되는 것 처럼 보이지만 꼭 그러진 않음
//        new YieldThread("스레드1").start();
//        new YieldThread("스레드2").start();

        ManyPrintThread t1 = new ManyPrintThread('-');
        ManyPrintThread t2 = new ManyPrintThread('|');
        t1.start();
        t2.start();
        // join을 하지 않은 경우 main이 먼저 끝나는 것을 확인 할 수 있다.
        // 하지만 join을 실행하는 경우 join을 진행하기 전의 스레드들은 계속해서 실행되고, join()을 실행한 스레드가 끝난뒤에야 그 후의 코드들이 실행된다.
        long start = System.currentTimeMillis();
        try {
            t1.join();   // t1이 끝날 때까지 main이 기다림
            t2.join();   // t2도
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("소요시간: " + (System.currentTimeMillis() - start) + "ms");
    }
}
// main에서 t1.sleep(2000)이라고 써도, 실제로 잠드는 건 t1이 아니라 그 코드를 실행 중인 main 스레드이다.
class printDash extends Thread {
    public void run() {
        for (int i = 0; i < 300; i++) {
            System.out.print("-");
        }
        try {
            Thread.sleep(2000);
        }catch (InterruptedException e){
            throw new RuntimeException();
        }
        System.out.println("<<종료>>");
    }
}

class printBar extends Thread {
    public void run() {
        for (int i = 0; i < 300; i++) {
            System.out.print("|");
        }
    }
}
//interrupt()를 실행한다고 스레드가 바로 멈추는 것이 아니라 isInterrupted를 사용해서 제어를 해야한다.
//interrupt() 를 실행하면 isInterrupted() 값이 false > true로 변경
class CountThread extends Thread {
    public void run() {
        int i = 10;
        while (i != 0 && !isInterrupted()) {   // 인터럽트 요청 오면 멈춤
            System.out.println(i--);
            for (long x = 0; x < 2_500_000_000L; x++) ;  // 시간 지연(busy)
        }
        System.out.println("카운트가 종료되었습니다.");
    }
}
//sleep상태에서 interrupt()  실행 시 interruptedException 발생
class CountSleepThread extends Thread {
    public void run() {
        int i = 10;
        while (i != 0 && !isInterrupted()) {
            System.out.println(i--);
            try {
                Thread.sleep(2000);   // 자고 있는 동안 interrupt가 오면?
            } catch (InterruptedException e) {
                System.out.println("자다가 깨어남! (InterruptedException)");
                break;   // 깨어나면 반복 종료
            }
        }
        System.out.println("카운트가 종료되었습니다.");
    }
}

class YieldThread extends Thread {
    private String name;
    public YieldThread(String name) { this.name = name; }
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name + " 실행 중. 반복: " + i);
            Thread.yield();   // 남은 실행시간 양보(힌트)
            try { Thread.sleep(500); } catch (InterruptedException e) { break; }
        }
    }
}

class ManyPrintThread extends Thread{
    char s;
    public ManyPrintThread(char s) {
        this.s = s;
    }
    public void run() {
        for (int i = 0; i < 5000; i++) {
            System.out.print(s);
        }
    }
}
