package interfaceVendingMachine;

public class Coffee implements Drink{
    private String name = "커가";
    private int price = 1000;
    private int balance = 3;

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
        System.out.println("향긋한 커피가 나왔습니다!");
    }

    @Override
    public int getBalance() {
        return this.balance;
    }

    @Override
    public void reduceBalance() {
        if(this.balance > 0) this.balance--;
        System.out.println("커피가 " + this.balance + "개 남았습니다.");
    }

    @Override
    public int compareTo(Drink o) {
        return o.getPrice() - this.getPrice();
    }
}
