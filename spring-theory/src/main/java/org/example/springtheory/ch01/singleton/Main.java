package org.example.springtheory.ch01.singleton;

public class Main {
    public static void main(String[] args) {
/*        TicketMachine w1 = TicketMachine.getInstance();
        TicketMachine w2 = TicketMachine.getInstance();
        TicketMachine w3 = TicketMachine.getInstance();

        System.out.println(w1.issue());   // 1
        System.out.println(w2.issue());   // 2  ← w2도 같은 기계라 이어짐!
        System.out.println(w1.issue());   // 3
        System.out.println(w3.issue());   // 4
        System.out.println(w1 == w2);     // true ← 완전히 같은 객체*/


        // 1) 버그: 기계 두 대 → 번호 중복
        NaiveTicketMachine a = new NaiveTicketMachine();
        NaiveTicketMachine b = new NaiveTicketMachine();
        System.out.println(a.issue() + ", " + b.issue());   // 1, 1

        // 2) 해결: 싱글톤은 하나뿐
        TicketMachine w1 = TicketMachine.getInstance();
        TicketMachine w2 = TicketMachine.getInstance();
        System.out.println(w1.issue() + ", " + w2.issue()); // 1, 2
        System.out.println(w1 == w2);                       // true

        // 3) lazy
        Settings s1 = Settings.getInstance();
        s1.setTheme("dark");
        System.out.println(Settings.getInstance().getTheme());  // dark
    }
}
