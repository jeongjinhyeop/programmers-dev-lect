package org.example.createjoinbyjpa.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponseDto {
    private boolean loggedIn;
    private String url;
    private String message;

    public static LoginResponseDto success(){
        return new LoginResponseDto(true,"/", "로그인에 성공했습니다.");
    }

    public static LoginResponseDto fail(){
        return new LoginResponseDto(false, null, "아이디와 비밀번호가 일치하지 않습니다.");
    }
}
