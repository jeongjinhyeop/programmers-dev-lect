package com.example.webservice.client;

import com.example.webservice.dto.SignUpRequestDto;
import com.example.webservice.dto.SignupResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "auth-service", url = "${edge-service.url:http://localhost:8080")
public interface AuthClient {

    @PostMapping("/api/users/join")
    SignupResponseDto join (@RequestBody SignUpRequestDto signUpRequestDto);
}
