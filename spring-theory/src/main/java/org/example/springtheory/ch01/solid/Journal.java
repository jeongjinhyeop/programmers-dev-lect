package org.example.springtheory.ch01.solid;

import java.util.ArrayList;

public class Journal {
    private ArrayList<String> entries = new ArrayList<>();
    void add(String text) { entries.add(text); }

    String getText() {
        StringBuilder sb = new StringBuilder();
        for (String e : entries) sb.append("- ").append(e).append("\n");
        return sb.toString();
    }

}
