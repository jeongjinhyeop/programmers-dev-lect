package org.example.springtheory.ch01.coffee;

import java.util.ArrayList;
import java.util.List;

public class Button {
    private ClickListener listener;
    private List<ClickListener> listeners = new ArrayList<>();
    public void setListener(ClickListener listener) {
        listeners.add(listener);
    }

    void press(){
        System.out.println("[시스템] 버튼이 눌렸습니다.");
        //Button 하나에 리스너 여러 개를 등록해 press() 시 전부 호출되게 해보기
        for (ClickListener l : listeners) {
            l.onClick();
        }
    }

}
