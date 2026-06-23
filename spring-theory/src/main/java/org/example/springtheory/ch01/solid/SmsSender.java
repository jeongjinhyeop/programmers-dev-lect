package org.example.springtheory.ch01.solid;

public class SmsSender implements MessageSender{
    @Override
    public void send(String msg) {
        System.out.println("[SMS] " + msg);
    }
}
