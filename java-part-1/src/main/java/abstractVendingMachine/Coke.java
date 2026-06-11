package abstractVendingMachine;

public class Coke extends Drink {
    public Coke(){
        super("콜라", 500, 3);
    }

    public Coke(int balance){
        super("콜라", 500, balance);
    }

    @Override
    public void dispense() {
        System.out.println("시원한 콜라가 나왔습니다.");
    }

    @Override
    public void balance() {
        System.out.println("콜라가 " + this.balance + "개 남았습니다.");
    }
}
