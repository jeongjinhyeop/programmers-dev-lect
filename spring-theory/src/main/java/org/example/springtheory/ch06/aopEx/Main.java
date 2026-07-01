package org.example.springtheory.ch06.aopEx;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(AopConfig.class);

        OrderService  orderService  = ctx.getBean(OrderService.class);
        MemberService memberService = ctx.getBean(MemberService.class);
        ProductService productService = ctx.getBean(ProductService.class);

        System.out.println("===== 주문 서비스 호출 =====");
        System.out.println(orderService.placeOrder("기계식 키보드"));

        System.out.println("\n===== 회원 서비스 호출 =====");
        System.out.println(memberService.register("kim"));

        System.out.println("\n===== 진짜 프록시인지 확인 =====");
        System.out.println("orderService 의 실제 타입: " + orderService.getClass());

        System.out.println();
        productService.getProduct("A-100");

        ctx.close();


        /*
        학습체크
         1. Advice / Pointcut / Advisor를 각각 한 줄로 설명할 수 있다
         2. proceed()가 무엇을 하는지, 그 앞뒤가 왜 부가기능 자리인지 안다
         3. DefaultAdvisorAutoProxyCreator가 빈을 언제, 어떻게 프록시로 바꾸는지 안다
         4. target 빈에는 왜 프록시 관련 설정이 없는지 설명할 수 있다
         5. 서비스를 추가해도 AOP 설정이 안 바뀌는 이유를 안다
         */
    }
}