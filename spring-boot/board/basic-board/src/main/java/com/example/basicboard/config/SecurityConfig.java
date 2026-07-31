package com.example.basicboard.config;


import com.example.basicboard.config.filter.TokenAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests( authorize -> authorize
                        .requestMatchers(
                                "/users/login",
                                "/users/join",
                                "/", // 페이지(HTML)는 공개, 데이터는 보호 - 브라우저 페이지 이동은 Bearer 헤더를 못 실으므로 페이지 인가는 API가 담당
                                "/api/users/login",
                                "/api/users/join",
                                "/api/tokens/refresh",

                                "/write",
                                "/detail/**",
                                "/stats",

                                "/css/**",
                                "/js/**",
                                "/access-denied",
                                "/error" // 404 등 에러 포워딩 경로. 막으면 인증 리다이렉트 루프가 생긴다.
                        ).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling( exception -> exception
                        .accessDeniedHandler(accessDeniedHandler())
                        .authenticationEntryPoint(authenticationEntryPoint())
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 아이디/비밀번호 검증의 진입점
    // form-login에서는 필터가 내부적으로 호출했지만,
    // 토큰 방식에서는 UserSerivce.login()이 직접 호출한다.
    // authentication() -> DaoAuthenticationProvider -> UserDetailService.loadUserByUsername() -> 비밀번호 대조
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix() // "ROLE_" 접두사 자동 부착
                .role("ADMIN").implies("USER")
                .build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return ((request, response, accessDeniedException) -> {
            if ( request.getRequestURI().startsWith("/api") ) {
                sendError( response, HttpServletResponse.SC_FORBIDDEN, "접근 권한이 없습니다." );
            } else {
                response.sendRedirect("/access-denied");
            }
        });
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return ((request, response, authException) -> {
            if ( request.getRequestURI().startsWith("/api") ) {
                sendError( response, HttpServletResponse.SC_UNAUTHORIZED, "인증이 필요합니다." );
            } else {
                response.sendRedirect("/access-denied");
            }
        });
    }

    private void sendError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("status code : " + status + ", message : " + message);
    }
}
