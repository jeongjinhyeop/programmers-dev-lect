package interfaceVendingMachine;

public class Fanta implements Drink{
    private String name = "환타";
    private int price = 300;
    private int balance = 7;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void dispense() {
        System.out.println("달콤한 환타가 나왔습니다!");
    }

    @Override
    public int getBalance() {
        return this.balance;
    }

    @Override
    public void reduceBalance() {
        if(this.balance > 0) this.balance--;
        System.out.println("환타가 " + this.balance + "개 남았습니다.");
    }

    @Override
    public int compareTo(Drink o) {
        return o.getPrice() - this.getPrice();
    }
}
