package org.example.springtheory.ch01.solid;

class JournalSaver {               // 책임 2: 보여주기/저장
    void print(Journal j) {
        System.out.print(j.getText());
    }
}
