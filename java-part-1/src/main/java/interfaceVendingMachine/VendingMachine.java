package interfaceVendingMachine;


import java.util.Arrays;

public class VendingMachine  {
    private int totalMoney;
    private Drink[] drinks;

    public VendingMachine() {
        totalMoney = 0;
        drinks = new Drink[] {           // 부모 타입 배열에 자식들을 담음
                new Coke(), new Cider(), new Fanta(), new Water(), new Coffee()
        };
        Arrays.sort(drinks);
    }

    public void insertMoney(int money) {
        totalMoney += money;
        System.out.println(money + "원을 넣었습니다.");
    }

    public void printMenu() {
        StringBuilder sb = new StringBuilder();
        System.out.println("============== 자판기 ==============");
//        System.out.println("[1]콜라 : 500  [2]사이다 : 500  [3]환타 : 300  [4]물 : 200 [5] 커피: 1000");
        //가격 변화에 따라 동적으로 음료 정보 출력
        for (int i = 0; i < drinks.length; i++){
            sb.append("[").append(i + 1).append("]").append(drinks[i].getName()).append(" : ").append(drinks[i].getPrice()).append(" ");
        }
        System.out.println(sb.toString());
        System.out.println("[6]돈 넣기  [7]종료");
        System.out.println("현재 금액 : " + totalMoney);
        System.out.println("====================================");
    }

    public void buy(int menuNumber) {
        Drink drink = drinks[menuNumber - 1];   // 어떤 음료든 Drink로 받음

        if(drink.getBalance() == 0){
            System.out.println("품절입니다.");
            return;
        }

        if (totalMoney < drink.getPrice()) {
            System.out.println("잔돈이 부족합니다.");
            return;
        }

        totalMoney -= drink.getPrice();
        drink.dispense();
        drink.defaultMethod();
        drink.reduceBalance();
    }

    public int returnChange() {
        int change = totalMoney;
        totalMoney = 0;
        return change;
    }
}
