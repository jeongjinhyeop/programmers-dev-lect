package com.example.oauth2practice.dto;

import lombok.Getter;

@Getter
public class SignInRequestDto {
    private String userId;
    private String password;
}
