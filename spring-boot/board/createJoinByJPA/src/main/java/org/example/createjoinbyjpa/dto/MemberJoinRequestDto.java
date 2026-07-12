package org.example.createjoinbyjpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter //jackson 라이브러리가 값을 채워주기 위해 세터를 작성
@NoArgsConstructor
//@AllArgsConstructor
public class MemberJoinRequestDto {
    private String userId;
    private String password;
    private String userName;
}
