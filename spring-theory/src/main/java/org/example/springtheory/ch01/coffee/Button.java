package org.example.springtheory.ch01.coffee;

public class Button {
    private ClickListener listener;
    public void setListener(ClickListener listener) {
        this.listener = listener;
    }

    void press(){
        System.out.println("[시스템] 버튼이 눌렸습니다.");
        listener.onClick();
    }

}
