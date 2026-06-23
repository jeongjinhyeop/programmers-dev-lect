package org.example.springtheory.ch01.singleton;

public class NaiveTicketMachine {
    private int lastNumber = 0;

    int issue() {
        lastNumber++;
        return lastNumber;
    }
}