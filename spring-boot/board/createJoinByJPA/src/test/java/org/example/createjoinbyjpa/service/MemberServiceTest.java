package org.example.createjoinbyjpa.service;

import org.example.createjoinbyjpa.domain.entity.Member;
import org.example.createjoinbyjpa.domain.repository.MemberRepository;
import org.example.createjoinbyjpa.dto.LoginRequestDto;
import org.example.createjoinbyjpa.dto.MemberJoinRequestDto;
import org.example.createjoinbyjpa.exception.DuplicateUserIdException;
import org.example.createjoinbyjpa.mapper.MemberMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    MemberRepository memberRepository;
    @Mock
    MemberMapper memberMapper;
    @InjectMocks
    MemberService memberService;


    @Test
    void login_성공() {
        //세터는 객체의 안정성이 떨어지기 때문에, 요즘 현대 자바(특히 스프링)에서는 가독성이 좋고 안전한 .builder() 방식을 절대적으로 많이 씁니다.
        //세터는 어디서나 값을 넣을 수 있기 때 (데이터 오염 발생)

        // [1단계: GIVEN] - 테스트를 하기 위한 모든 준비 단계
        // 1. 테스트 할 멤버 데이터 객체 생성 (가짜 DB 결과물로 쓰일 엔티티)
        Member member = Member.builder()
                .userId("hong")
                .password("1234")
                .userName("홍길동")
                .build();
        // * 스터빙 : given안의 상황이 발생했을 때 willReturn의 값을 반환해
        // DB와의 통신을 직접적으로 하면 무겁고 느려지기 때문에 테스트의 의미가 희석된다.
        // Optional.of(value): value의 값이 무조건 null이 아니라고 할때 사용하는 코드이다.
        // null이 들어갈 시 NPE발생

        // 2. given으로 테스트 할 시나리오(원인-결과) 정의
        given(memberRepository.findByUserId("hong")).willReturn(Optional.of(member));


        // 3. dto에 요청 받은 값 가정하여 세팅
        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setUserId("hong"); //수업때는 UserId값을 UserName 필드에 넣어서 사용함
        requestDto.setPassword("1234");

        // [2단계: WHEN] - 실제 테스트 대상 기능을 "실행"하는 단계
        // 4. 단위 기능 수행 (우리가 진짜 검증하고 싶은 핵심 비즈니스 로직)
        Optional<Member> result = memberService.login(requestDto);

        // [3단계: Then] - 결과 값 검증
        // 5. 결과가 올바른지 확인
        assertThat(result).isPresent();
        // assertThat(result)
        // result를 검증하겠다.
        // .isPresent()
        // 원하는 결과값 true니? 라고 검증
    }

    @Test
    void join_중복이면_예외() {
        MemberJoinRequestDto req = new MemberJoinRequestDto();
        req.setUserId("hong");
        given(memberRepository.existsByUserId("hong")).willReturn(true);

        assertThatThrownBy(() -> memberService.join(req)) //예외(에러)가 발생하는지 지켜보겠다
                .isInstanceOf(DuplicateUserIdException.class); //(아이디 중복 예외) 타입이 맞는지 확인
        verify(memberRepository, never()).save(any()); // 중복 체크는 정상적으로 하되, 에러가 터졌으니 DB에 저장하는 save 단계까지 넘어가지는 않겠다.
    }
}