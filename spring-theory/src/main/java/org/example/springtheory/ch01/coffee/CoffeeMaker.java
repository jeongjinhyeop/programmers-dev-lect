package org.example.springtheory.ch01.coffee;

public class CoffeeMaker {
    //객체 생성을 직접 하지 않게 변경
    private Bean bean;
    public CoffeeMaker(Bean bean){
        this.bean = bean;
        }
    void brew(){
        System.out.println(bean.getName() + "로 커피를 내립니다.");
    }
}
