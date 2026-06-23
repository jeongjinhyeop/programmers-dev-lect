package org.example.springtheory.ch01.singleton2;

public class SimpleConnectionMaker implements ConnectionMaker {     // 이것도 싱글톤
    private static final SimpleConnectionMaker instance = new SimpleConnectionMaker();
    private SimpleConnectionMaker() {}
    static SimpleConnectionMaker getInstance() { return instance; }
    public String makeConnection() { return "DB연결"; }
}