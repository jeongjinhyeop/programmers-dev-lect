package org.example.createjoinbyjpa.mapper;

import org.example.createjoinbyjpa.domain.entity.Member;
import org.example.createjoinbyjpa.dto.MemberJoinRequestDto;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member toEntity(MemberJoinRequestDto request) {
        return Member.builder()
                .userId(request.getUserId())
                .userName(request.getUserName())
                .password(request.getPassword())
                .build();
    }
}
