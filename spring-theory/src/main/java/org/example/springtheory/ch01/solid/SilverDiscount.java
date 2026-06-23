package org.example.springtheory.ch01.solid;

public class SilverDiscount implements DiscountPolicy{
    public int discount(int price) {
        return price * 85 / 100;
    }
}
