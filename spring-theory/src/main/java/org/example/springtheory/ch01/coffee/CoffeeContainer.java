package org.example.springtheory.ch01.coffee;

public class CoffeeContainer {
    private CoffeeMaker colombiaMaker;
    private CoffeeMaker ethiopiaMaker;

    public CoffeeContainer() {
        MilkFrother milkFrother = new MilkFrother();
        //싱글턴을 채택한 이유
        /*
        1. 객체 생성 비용 절약
        2. 메모리 효율
        3. 대부분의 서비스 객체는 상태가 없음  > 객체 내부의 필드가 요청마다 바뀌는 데이터를 저장하지 않는다는 뜻 ex) service
        4. 객체 공유와 관리가 편함
        5. 성능과 확장성에 유리

        */

        colombiaMaker = new CoffeeMaker(new ColombiaBean(), milkFrother);
        ethiopiaMaker = new CoffeeMaker(new EthiopiaBean(), milkFrother);
    }
    public CoffeeMaker getCoffeeMaker(String type){
        //외부(메인)에서 생성자를 CoffeeMaker에게 주입
        Bean bean;
        MilkFrother milkFrother = new MilkFrother();
        if (type.equals("colombia")) {
            return colombiaMaker;
        } else if (type.equals("ethiopia")){
            return ethiopiaMaker;
        } else {
            throw  new IllegalArgumentException("지원하지 않는 원두입니다.");
        }
    }
}
