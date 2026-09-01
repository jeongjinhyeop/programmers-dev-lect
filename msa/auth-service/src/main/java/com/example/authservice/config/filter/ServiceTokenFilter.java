package com.example.authservice.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Slf4j
@Component
public class ServiceTokenFilter extends OncePerRequestFilter {

    public static final String SERVICE_TOKEN_HEADER = "X_Service_Token";

    private static final Set<String> INTERNAL_API_PATHS = Set.of(
            "/api/users/names"
    );

    @Value("${Service.token}")
    private String serviceToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(INTERNAL_API_PATHS.contains(request.getRequestURI())) {
            log.info("INTERNAL_API_PATHS contains : {}", request.getRequestURI());
            String token = request.getHeader(SERVICE_TOKEN_HEADER);

            if(!serviceToken.equals(token)) {
                log.warn("Service token header mismatch : {}", request.getRequestURI());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("status code: 401, message : 서비스 간 인증이 필요합니다.");
                return;
            }
        }
    }
}
