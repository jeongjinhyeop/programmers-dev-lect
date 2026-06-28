package org.example.springtheory.strategyPattren;

import java.util.ArrayList;
import java.util.List;
class Database {
    private List<User> users = new ArrayList<>();
    void open()  { System.out.println("  [컨텍스트] 연결 열기"); }
    void close() { System.out.println("  [컨텍스트] 연결 닫기"); }
    List<User> getUsers() { return users; }
}