package org.example.springtheory.ch05.ex_5_4.service;

public class Mail {
    private String to;
    private String subject;
    private String text;

    public Mail(String to, String subject, String text) {
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }
}
