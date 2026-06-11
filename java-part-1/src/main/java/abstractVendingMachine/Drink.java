package abstractVendingMachine;

public abstract class Drink {
    protected String name;
    protected int price;
    protected int balance;

    public Drink(String name, int price, int balance) {
        this.name = name;
        this.price = price;
        this.balance = balance;
    }

    public Drink(String name, int price){
        this(name, price, 5);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public abstract void dispense();
    public abstract void balance();

}
