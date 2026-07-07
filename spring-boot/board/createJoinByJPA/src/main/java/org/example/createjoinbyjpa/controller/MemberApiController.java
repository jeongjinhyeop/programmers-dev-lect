package org.example.createjoinbyjpa.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.createjoinbyjpa.constant.SessionConst;
import org.example.createjoinbyjpa.dto.LoginRequestDto;
import org.example.createjoinbyjpa.dto.LoginResponseDto;
import org.example.createjoinbyjpa.dto.MemberJoinRequestDto;
import org.example.createjoinbyjpa.dto.MemberJoinResponseDto;
import org.example.createjoinbyjpa.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@AllArgsConstructor
public class MemberApiController {
    private MemberService memberService;

    @PostMapping("/join")
    public MemberJoinResponseDto join(@RequestBody MemberJoinRequestDto request){
        memberService.join(request);

        return new MemberJoinResponseDto("/members/login");
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto request, HttpSession session){
        System.out.println("controller getUserId: " + request.getUserId());
        System.out.println("controller getPassword: " + request.getPassword());
        return memberService.login(request)
                .map(member -> {
                    session.setAttribute(SessionConst.USER_ID, member.getUserId());
                    session.setAttribute(SessionConst.USER_NAME, member.getUserName());
                    return  LoginResponseDto.success();
                })
                .orElseGet(LoginResponseDto::fail);
    }
}
