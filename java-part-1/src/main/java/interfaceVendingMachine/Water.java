package interfaceVendingMachine;

public class Water implements Drink{
    private String name = "물";
    private int price = 200;
    private int balance = 10;

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
        System.out.println("깔끔한 물이 나왔습니다.");
    }

    @Override
    public int getBalance() {
        return this.balance;
    }

    @Override
    public void reduceBalance() {
        if(this.balance > 0) this.balance--;
        System.out.println("물이 " + this.balance + "개 남았습니다.");
    }

    @Override
    public int compareTo(Drink o) {
        return o.getPrice() - this.getPrice();
    }
}
