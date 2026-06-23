package org.example.springtheory.ch01.solid;

public class VipDiscount implements DiscountPolicy {
    public int discount(int price) {
        return price * 80 / 100;
    }
}
