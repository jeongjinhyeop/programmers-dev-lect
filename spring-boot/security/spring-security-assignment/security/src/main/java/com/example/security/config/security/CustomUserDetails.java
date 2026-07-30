package com.example.security.config.security;

import com.example.security.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Builder
//"DB 엔티티(Entity) 데이터를 스프링 시큐리티가 읽을 수 있는 언어(규칙)로 번역해 놓은 어댑터(Adapter)
// 우리가 만든 DB 테이블과 매핑되는 User 엔티티는 개발자가 만든 자유로운 클래스
// 하지만 스프링 시큐리티는 이 클래스가 필드명을 userId로 썼는지 email로 썼는지, 비밀번호를 pwd로 썼는지 알 길이 없음
// DB 유저가 어떻게 생겼든 상관없는데, 내가 만들어둔 UserDetails 인터페이스 규격은 맞춰서 줘! 라고 요구하는 것
public class CustomUserDetails implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(user.getRole().name())
        );
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠기지 않았는가 (로그인 연속 실패 잠금 등)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는가 (주기적 변경 강제 정책 등)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성 상태인가 (탈퇴/이메일 미인증 등)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
