package org.example.springtheory.ch01.coffee;

public class CoffeeMaker {
    //객체 생성을 직접 하지 않게 변경
    private Bean bean;
    private MilkFrother milkFrother;

    public CoffeeMaker(Bean bean, MilkFrother milkFrother){
        this.bean = bean;
        this.milkFrother = milkFrother;
        }

    void brew(){
        System.out.println(
                bean.getName() + "로 커피를 내리고 " + milkFrother.froth() + "를 추가합니다.");
    }
}