package com.example.security.service;

import com.example.security.config.security.CustomUserDetails;
import com.example.security.domain.entity.User;
import com.example.security.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService { //CustomUserDetailsService라는 명칭으로도 자주 사용

    private final UserRepository userRepository;

    //스프링 시큐리티에는 로그인 요청이 들어왔을 때 유저를 검증하는 DaoAuthenticationProvider 가 내부적으로 loadUserByUsername 호출
    //개발자가 만든 loadUserByUsername이 실행되면서 DB에서 유저 정보를 가져와 CustomUserDetails에 담아 시큐리티에게 넘겨줍니다.
    //시큐리티는 넘겨받은 CustomUserDetails 안의 (암호화된) 비밀번호와, 사용자가 입력한 비밀번호가 일치하는지 PasswordEncoder로 검사
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));

        return CustomUserDetails.builder()
                .user(user)
                .build();
    }
}
