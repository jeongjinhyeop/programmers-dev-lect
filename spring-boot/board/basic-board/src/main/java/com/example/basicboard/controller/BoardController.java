package com.example.basicboard.controller;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BoardController {

    @GetMapping("/")
    public String boardList(HttpSession session, Model model) {

        return "board-list";
    }

    public void setSession(HttpSession session, Model model){
        String userId = (String) session.getAttribute("userId");
        String userName = (String) session.getAttribute("userName");

        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);
    }


}
