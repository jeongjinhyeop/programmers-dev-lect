package org.example.springtheory.ch01.solid;

public class Main {
    public static void main(String[] args) {
        //SRP
        System.out.println("===== SRP: 단일 책임 =====");
        Journal j = new Journal();
        JournalSaver js = new JournalSaver();
        j.add("오늘은 자바를 배웠다");
        j.add("SOLID는 어렵지만 재밌다");
        js.print(j);


        // OCP: 인터페이스 배열로 묶어서 한 번에
        System.out.println("===== OCP: 개방-폐쇄 =====");
        //1. 비즈니스 로직은 건들지 않음
        // 현재는 IoC가 적용되지 않은 상태라 직접 객체 호출
        DiscountPolicy[] policies = { new BasicDiscount(),new SilverDiscount(), new GoldDiscount(), new VipDiscount() };
        String[] names = { "일반","실버", "골드", "VIP" };
        for (int i = 0; i < policies.length; i++) {
            System.out.println(names[i] + " 회원 -> " + policies[i].discount(10000) + "원");
        }

        // LSP: Bird 타입으로 묶어도 안 터짐
        System.out.println("===== LSP: 리스코프 치환 =====");
        Bird[] birds = { new Sparrow(), new Penguin() };
        for (Bird b : birds) b.eat();
        ((Sparrow) birds[0]).fly();
        ((Penguin) birds[1]).swim(); //자식만 구현한 메서드는 부모 타입에서 사용 불가


        //ISP
        System.out.println("===== ISP: 인터페이스 분리 =====");
        MessageSender[] ms = {new EmailSender(), new SmsSender()};
        for (int i = 0; i < ms.length; i++) {
            ms[i].send("주문이 완료되었습니다");
        }

        // DIP: 구현체를 '주입'으로 교체
        //ex) 컨트롤러 내부에 new가 사라지고 생성자 파라미터로 주입받으면서, 제어권이 스프링에게 완전히 넘어가 버림 (IoC, 제어의 역전).
        //컨트롤러는 객체를 직접 만들지 않고(new X), 인터페이스 변수만 선언한 뒤 생성자 파라미터로 받도록 대기한다.
        System.out.println("===== DIP: 의존관계 역전 =====");
        new NotificationService(new EmailSender()).notifyUser("주문이 완료되었습니다");
        new NotificationService(new SmsSender()).notifyUser("주문이 완료되었습니다");
    }
}