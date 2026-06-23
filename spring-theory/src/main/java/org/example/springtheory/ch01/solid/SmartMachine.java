package org.example.springtheory.ch01.solid;

public class SmartMachine implements Printer, Scanner{
    @Override
    public void print() {
        System.out.println("복합기: 인쇄");
    }

    @Override
    public void scan() {
        System.out.println("복합기: 스캔");
    }
}
