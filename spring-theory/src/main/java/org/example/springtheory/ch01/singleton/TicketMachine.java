package org.example.springtheory.ch01.singleton;

class TicketMachine {
    private static final TicketMachine instance = new TicketMachine(); // ① 유일 객체
    private int lastNumber = 0;
    /*
    * 자바에서 new TicketMachine()을 호출한다는 것은 "TicketMachine 클래스의 생성자를 실행해라"라는 뜻.
    * 그런데 생성자 앞에 private이 붙으면, 이 생성자는 오직 자기 자신(TicketMachine 클래스 내부)에서만 호출할 수 있는
    * */

    private TicketMachine() {}                                 // ② new 차단
    static TicketMachine getInstance() { return instance; }    // ③ 유일 출입구

    int issue() { lastNumber++; return lastNumber; }
}
