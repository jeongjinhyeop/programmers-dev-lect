package org.example.springtheory.ch01.coffee;

public class CoffeeContainer {

    public CoffeeMaker getCoffeeMaker(){
        //외부(메인)에서 생성자를 CoffeeMaker에게 주입
        Bean bean = new ColombiaBean();
        return new CoffeeMaker(bean);
    }
}
