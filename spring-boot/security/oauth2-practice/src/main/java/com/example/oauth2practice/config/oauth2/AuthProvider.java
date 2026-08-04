package com.example.oauth2practice.config.oauth2;

public enum AuthProvider {
    LOCAL,
    KAKAO;

    public static AuthProvider from(String registrationId) {
        return AuthProvider.valueOf(registrationId.toUpperCase());
    }
}
