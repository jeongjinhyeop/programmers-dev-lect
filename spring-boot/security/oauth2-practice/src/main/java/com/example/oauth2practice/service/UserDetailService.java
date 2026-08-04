package com.example.oauth2practice.service;


import com.example.oauth2practice.config.security.CustomUserDetails;
import com.example.oauth2practice.domain.entity.User;
import com.example.oauth2practice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    // [일반 로그인 전용] 입력받은 username(아이디)으로 DB에서 유저를 조회합니다.
    // OAuth2 로그인은 customOAuth2UserService를 타지만,
    // 자체 회원가입 후 아이디, 비밀번호로 로그인하는 기능이 남아있어 이 메서드가 필요하다
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));

        return CustomUserDetails.builder()
                .user(user)
                .build();
    }
}