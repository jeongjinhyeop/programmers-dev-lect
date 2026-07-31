package com.example.basicboard.dto;

import com.example.basicboard.domain.entitiy.Role;
import com.example.basicboard.domain.entitiy.User;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

    private String userId;
    private String password;
    private String userName;
    private Role role;

    public User toUser(String encodedPassword) {
        return User.builder()
                .userId(userId)
                .password(encodedPassword)
                .name(userName)
                .role(role != null ? role : Role.ROLE_USER)
                .build();
    }

}