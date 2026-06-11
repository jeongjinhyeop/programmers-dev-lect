package interfaceVendingMachine;

public class Coke implements Drink{
    private String name = "콜라";
    private int price = 500;
    private int balance = 5;

    @Override public String getName() { return name; }
    @Override public int getPrice()   { return price; }
    @Override public void dispense() {
        System.out.println("시원한 콜라가 나왔습니다!");
    }

    @Override
    public int getBalance() {
        return this.balance;
    }

    @Override
    public void reduceBalance() {
        if(this.balance > 0) this.balance--;
        System.out.println("콜라가 " + this.balance + "개 남았습니다.");
    }

    @Override
    public int compareTo(Drink o) {
        return o.getPrice() - this.getPrice();
    }
}