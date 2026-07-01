package org.example.springtheory.ch05.ex_5_4.service;

import java.util.ArrayList;
import java.util.List;

public class MockMailSender implements MailSender{
    private List<String> sendTo = new ArrayList<>();

    @Override
    public void send(Mail mail) {

    }

    public List<String> getSendTo(){

        return sendTo;
    }
}
