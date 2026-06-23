package org.example.springtheory.ch01.solid;

public class GoldDiscount implements DiscountPolicy{
    public int discount(int price) {
        return price * 90 / 100;
    }
}
