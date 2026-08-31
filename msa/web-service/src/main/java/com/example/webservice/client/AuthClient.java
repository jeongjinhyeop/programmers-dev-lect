package com.example.webservice.client;

import com.example.webservice.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "auth-service", url = "${edge-service.url:http://localhost:8000}")
public interface AuthClient {

    @PostMapping("/api/users/join")
    SignUpResponseDto join(@RequestBody SignUpRequestDto signUpRequestDto);

    @PostMapping("/api/users/login")
    ResponseEntity<SignInResponseDto> login(@RequestBody SignInRequestDto signInRequestDto);

    @GetMapping("/api/users/info")
    UserInfoResponseDto getUserInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization);
}
