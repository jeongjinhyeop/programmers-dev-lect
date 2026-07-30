package com.example.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SignInResponseDto {
    private boolean  isLoggedIn;
    private String url;
    private String userName;
    private String userId;
    private String message;
}
