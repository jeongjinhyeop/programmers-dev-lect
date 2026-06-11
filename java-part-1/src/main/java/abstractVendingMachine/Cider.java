package abstractVendingMachine;

public class Cider extends Drink{

    public Cider() {
        super("사이다", 300, 5);
    }

    public Cider(int balance) {
        super("사이다", 300, balance);
    }

    @Override
    public void dispense() {
        System.out.println("톡 쏘는 사이다가 나왔습니다.");
    }

    @Override
    public void balance() {
        System.out.println("사이다가 " + this.balance + "개 남았습니다.");
    }
}
