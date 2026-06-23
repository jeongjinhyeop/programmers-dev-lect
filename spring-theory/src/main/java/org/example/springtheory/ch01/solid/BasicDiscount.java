package org.example.springtheory.ch01.solid;

public class BasicDiscount implements DiscountPolicy{
    public int discount(int price) {
        return price;
    }
}
