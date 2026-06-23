package org.example.springtheory.ch01.solid;

public class SimplePrinter implements Printer{
    @Override
    public void print() {
        System.out.println("구형 프린터: 인쇄만 합니다");
    }
}
