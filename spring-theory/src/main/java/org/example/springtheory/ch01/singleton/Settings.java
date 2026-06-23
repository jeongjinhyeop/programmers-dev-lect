package org.example.springtheory.ch01.singleton;

class Settings {
    private static Settings instance;          // null로 시작 (미리 안 만듦)
    private String theme = "dark";

    private Settings() {}
    static Settings getInstance() {
        if (instance == null) {                // 처음 호출 때 딱 한 번 생성
            instance = new Settings();
        }
        return instance;
    }
    String getTheme()       { return theme; }
    void   setTheme(String t){ theme = t;    }
}
