package com.example.webservice.service;

import com.example.webservice.client.AuthClient;
import com.example.webservice.dto.SignUpRequestDto;
import com.example.webservice.dto.SignupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthClient authClient;

    public SignupResponseDto signUp (SignUpRequestDto signUpRequestDto) {
        return authClient.join(signUpRequestDto);
    }
}
