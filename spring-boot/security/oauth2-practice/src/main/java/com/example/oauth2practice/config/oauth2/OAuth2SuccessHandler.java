package com.example.oauth2practice.config.oauth2;

import com.example.oauth2practice.config.jwt.JwtProperties;
import com.example.oauth2practice.config.jwt.TokenProvider;
import com.example.oauth2practice.service.TokenService;
import com.example.oauth2practice.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;
    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //인증 성공시 principal은 CustomOAuth2Service가 반환했던 CustomOAuth2User()다.
        CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();

        String targetUrl;
        if(principal.isRegistered()) {
            // 기존 회원
            TokenService.TokenPair tokens = tokenService.issueToken(principal.getUser());
            CookieUtil.addCookie(
                    response,
                    CookieUtil.REFRESH_TOKEN_COOKIE,
                    tokens.refreshToken(),
                    (int) jwtProperties.getRefreshTokenValidity().toSeconds()
            );
            targetUrl = "/";
        } else {
            // 미가입
            // 10분짜리 "가입 토큰"을 발급해 가입 동의 페이지로 보낸다.
            String signupToken = tokenProvider.createSignupToken(principal.getProvider(), principal.getUserInfo());

            targetUrl = UriComponentsBuilder.fromUriString("/users/oauth-join")
                    .queryParam("signupToken", signupToken)
                    .build()
                    .toUriString();
        }

        //이미 응답이 커밋되었다면 리다이렉트가 불가능하므로 방어적으로 빠져나간다.
        if( response.isCommitted()) {
            log.debug("Response has already been committed");
            return;
        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
