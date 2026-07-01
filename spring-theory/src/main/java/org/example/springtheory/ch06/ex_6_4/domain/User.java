package org.example.springtheory.ch06.ex_6_4.domain;

import org.example.springtheory.ch06.ex_6_4.dao.Level;

// 사용자 정보를 저장할 User클래스
public class User {

    private String id;
    private String name;
    private String password;
    private Level level;
    private int login;
    private int recommand;

    // * 레벨 업그레이드 동작을 'User 자신'이 갖는다.
    //  - "다음 레벨로 올린다"는 것은 사용자 데이터에 대한 처리이므로, 그 책임을 User에 둔다.
    //  - 이렇게 해두면 UserService는 '언제 올릴지'만 판단하고, '어떻게 올릴지'는 User에 맡길 수 있다.
    public void upgradeLevel() {
        this.level = this.level.nextLevel();
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public int getRecommand() {
        return recommand;
    }

    public void setRecommand(int recommand) {
        this.recommand = recommand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
