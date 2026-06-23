package org.example.springtheory.ch01.coffee;

public class Main {
    public static void main(String[] args) {
        System.out.println("2. DI: 제어를 바깥으로");
        //step 2. DI 외부(메인)에서 생성자를 CoffeeMaker에게 주입
        new CoffeeMaker(new ColombiaBean(), new MilkFrother()).brew();
        new CoffeeMaker(new EthiopiaBean(), new MilkFrother()).brew();

        //step3. 조립까지 위임
        System.out.println("\n3. IOC 컨테이너");
//        CoffeeContainer container = new CoffeeContainer();
        CoffeeMaker maker = new CoffeeContainer().getCoffeeMaker("ethiopia");
        maker.brew();

        //step4. 할리우드 원칙 : 실행이 언제될지 모름 프레임워크가 알아서 내 코드를 실행함
        System.out.println("\n4. 할리우드 원칙");
        Button button = new Button();
        button.setListener(new LikeAction());
        button.setListener(new LikeAction());
        button.press();
    }
}
