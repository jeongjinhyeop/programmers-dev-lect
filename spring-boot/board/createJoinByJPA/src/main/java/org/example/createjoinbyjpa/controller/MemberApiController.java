package org.example.createjoinbyjpa.controller;

import lombok.AllArgsConstructor;
import org.example.createjoinbyjpa.dto.MemberJoinRequestDto;
import org.example.createjoinbyjpa.dto.MemberJoinResponseDto;
import org.example.createjoinbyjpa.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
