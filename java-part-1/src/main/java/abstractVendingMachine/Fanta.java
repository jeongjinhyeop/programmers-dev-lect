package abstractVendingMachine;

public class Fanta extends Drink{

    public Fanta (){
        super("Fanta", 300);
    }

    public Fanta (int balance){
        super("Fanta", 300, 3);
    }

    @Override
    public void dispense() {
        System.out.println("달콤한 환타가 나왔습니다!");
    }

    @Override
    public void balance() {
        System.out.println("환타가 " + this.balance + "개 남았습니다.");
    }

}
