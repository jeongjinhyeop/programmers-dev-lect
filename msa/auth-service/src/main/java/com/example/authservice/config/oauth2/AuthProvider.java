package com.example.authservice.config.oauth2;

public enum AuthProvider {
    LOCAL,
    KAKAO;

    public AuthProvider from(
            String registrationId
    ){
        return AuthProvider.valueOf(registrationId.toUpperCase());
    }
}
