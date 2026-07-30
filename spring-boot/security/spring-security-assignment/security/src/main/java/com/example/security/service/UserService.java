package com.example.security.service;

import com.example.security.domain.entity.User;
import com.example.security.domain.repository.UserRepository;
import com.example.security.dto.SignUpRequestDto;
import com.example.security.exception.DuplicateUserIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignUpRequestDto request){
        if(userRepository.existsByUserId(request.getUserId())){
            throw new DuplicateUserIdException("이미 사용중인 아이디 입니다.");
        }

        User user = request.toUser(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }
}
