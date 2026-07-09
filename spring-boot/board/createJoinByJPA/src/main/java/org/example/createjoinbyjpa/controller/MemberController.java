package org.example.createjoinbyjpa.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
public class MemberController {

    @GetMapping("/join")
    public String join() {
        return "sign-up";
    }

    @GetMapping("/login")
    public String login() {
        return "sign-in";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // 자신의 JSESSIONID를 삭제하는 것이라 다른 사람의 세션에는 영향이 없다.

        //return "sign-in"; 을 사용하지 않는 이유
        //상태를 바꾸는 요청(로그아웃) 뒤엔 리다이텍트해서 새로고침 . 로그아웃이 재실행 되는 것을 막고
        //주소창도 /members/login을 맞춘다.
        return "redirect:/members/login";
    }

}
