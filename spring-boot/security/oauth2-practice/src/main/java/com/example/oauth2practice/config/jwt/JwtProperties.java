package com.example.oauth2practice.config.jwt;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Getter
@Setter
@Component//application.yml의 설정을 이 클래스에 자동으로 주입(바인딩)하기 위해 빈으로 등록
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String issuer;
    private String secretKey;
    private Duration accessTokenValidity;
    private Duration refreshTokenValidity;
}
