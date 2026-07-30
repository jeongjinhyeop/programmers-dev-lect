package com.example.formlogin.service;

import com.example.formlogin.config.security.CustomUserDetails;
import com.example.formlogin.domain.entity.User;
import com.example.formlogin.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("[로그인]" + username + "NOT FOUND")
                );

        return CustomUserDetails.builder()
                .user(user)
                .build();
    }
}
