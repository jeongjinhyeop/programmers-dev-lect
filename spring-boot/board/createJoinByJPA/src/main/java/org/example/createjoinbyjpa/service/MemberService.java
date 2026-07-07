package org.example.createjoinbyjpa.service;

import lombok.RequiredArgsConstructor;
import org.example.createjoinbyjpa.domain.entity.Member;
import org.example.createjoinbyjpa.domain.repository.MemberRepository;
import org.example.createjoinbyjpa.dto.LoginRequestDto;
import org.example.createjoinbyjpa.dto.MemberJoinRequestDto;
import org.example.createjoinbyjpa.dto.MemberJoinResponseDto;
import org.example.createjoinbyjpa.exception.DuplicateUserIdException;
import org.example.createjoinbyjpa.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Transactional
    public void join(MemberJoinRequestDto request) {
        if(memberRepository.existsByUserId(request.getUserId())) {
            throw new DuplicateUserIdException("이미 존재하는 아이디입니다.");
        }
        Member member = memberMapper.toEntity(request);
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Optional<Member> login (LoginRequestDto request){
        System.out.println(request.getPassword());
        System.out.println(request.getUserId());
        return memberRepository.findByUserId(request.getUserId())
                .filter(member ->
                    member.getPassword().equals(request.getPassword())
                );
    }
}
