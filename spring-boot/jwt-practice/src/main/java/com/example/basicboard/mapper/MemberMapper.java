package com.example.basicboard.mapper;

import com.example.basicboard.domain.entitiy.Member;
import com.example.basicboard.dto.MemberJoinRequestDto;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member toEntity(MemberJoinRequestDto dto){
        return Member.builder()
                .userId(dto.getUserId())
                .userName(dto.getUserName())
                .password(dto.getPassword())
                .build();
    }

}
