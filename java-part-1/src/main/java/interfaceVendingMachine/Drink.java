package interfaceVendingMachine;


public interface Drink extends Comparable<Drink>{
    String getName();
    int getPrice();
    void dispense();
    int getBalance();       // 현재 수량을 확인하는 메서드
    void reduceBalance();   // 수량을 하나 줄이는 메서드

    default void defaultMethod(){
        System.out.println("인터페이스 디폴트 메서드 테스트!");
    }
}

