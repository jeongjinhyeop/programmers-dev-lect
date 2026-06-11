package abstractVendingMachine;

public class Coffee extends Drink {
    public Coffee(int balance) {
        super("커피", 1000, balance);
    }

    public Coffee() {
        super("커피", 1000, 5);
    }

    @Override
    public void dispense() {
        System.out.println("향긋한 커피가 나왔습니다!");
    }

    @Override
    public void balance() {
        System.out.println("커피가 " + this.balance + "개 남았습니다.");
    }
}
