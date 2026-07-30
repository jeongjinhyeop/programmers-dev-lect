package com.example.security.controller;

import com.example.security.dto.SignUpRequestDto;
import com.example.security.dto.SignUpResponseDto;
import com.example.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {
    private final UserService userService;

    @PostMapping("/join")
    public SignUpResponseDto join(@RequestBody SignUpRequestDto request) {
        userService.signUp(request);
        return new SignUpResponseDto("/users/login");
    }
}
