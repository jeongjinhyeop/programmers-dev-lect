package com.example.basicboard.service;

import com.example.basicboard.config.security.CustomUserDetails;
import com.example.basicboard.domain.entitiy.User;
import com.example.basicboard.domain.repository.MemberRepository;
import com.example.basicboard.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));

        return CustomUserDetails.builder()
                .user(user)
                .build();
    }
}