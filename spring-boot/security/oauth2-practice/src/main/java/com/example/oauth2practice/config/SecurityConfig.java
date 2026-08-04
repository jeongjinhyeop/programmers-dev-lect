package com.example.oauth2practice.config;

import com.example.oauth2practice.config.filter.TokenAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/users/login",
                                "/users/join",
                                "/users/oauth-join",
                                "/",  // 페이지(HTML)는 공개, 데이터는 보호 - 브라우저 페이지 이동은 Bearer 헤더를 못 실으므로 페이지 인가는 API가 담당
                                "/admin",

                                "/api/users/login",
                                "/api/users/join",
                                "/api/tokens/refresh",
                                "/api/users/oauth-join",

                                "/css/**",
                                "/js/**",
                                "/access-denied",
                                "/error" )
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler())
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    //인증 실패시
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return ((request, response, authException) -> {
            if ( request.getRequestURI().startsWith("/api") ) {
                sendError( response, HttpServletResponse.SC_UNAUTHORIZED, "인증이 필요합니다." );
            } else {
                response.sendRedirect("/access-denied");
            }
        });
    }

    @Bean
    //인가 실패시
    public AccessDeniedHandler accessDeniedHandler() {
        return ((request, response, accessDeniedException) -> {
            if ( request.getRequestURI().startsWith("/api") ) {
                sendError( response, HttpServletResponse.SC_FORBIDDEN, "접근 권한이 없습니다." );
            } else {
                response.sendRedirect("/access-denied");
            }
        });
    }

    private void sendError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json; charset=UTF-8");
        //HTTP 응답의 바디(Body)에 직접 문자열 데이터를 써서 클라이언트로 전송
        response.getWriter().write("status code : " + status + "message: " + message);
    }
}
