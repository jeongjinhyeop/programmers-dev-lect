package abstractVendingMachine;

public class Water extends Drink{

    public Water() {
        super("물", 200, 10);
    }

    public Water(int balance) {
        super("물", 200, balance);
    }

    @Override
    public void dispense() {
        System.out.println("깔끔한 물이 나왔습니다.");
    }

    @Override
    public void balance() {
        System.out.println("물이 " + this.balance + "개 남았습니다.");
    }
}
