package com.example.oauth2practice.dto;

import com.example.oauth2practice.domain.entity.Role;
import lombok.Getter;

@Getter
public class OAuthSignUpRequestDto {
    private String signupToken;
    private Role role;
}