package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .httpBasic(basic -> basic.realmName("Test"))
                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/users/join",
                                "/api/users/join",
                                "/css/**",
                                "/js/**"
                                ).permitAll().anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/users/login")
                        .loginProcessingUrl("/users/login")
                        .usernameParameter("userId")
                        .passwordParameter("password")
                        .permitAll()
                ).logout(logout -> logout
                        .logoutUrl("/users/logout")
                        .logoutSuccessUrl("/users/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return  http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        UserDetails user = User.withUsername("user")
//                .password(passwordEncoder().encode("12345"))
//                .authorities("USER")
//                .build();
//
//        manager.createUser(user);
//
//        return manager;
//    }

 /*   @Bean
    public PasswordEncoder passwordEncoder() {
        //Spring Security는 설계 구조상 모든 비밀번호 비교는 무조건 PasswordEncoder를 거친다
        //Spring Security 내부 메커니즘은 무조건 PasswordEncoder.matches(입력값, 저장된값)를 호출
        //패스워드를 받아서 데이터베이스나 메모리 등에 저장할 때, 바로 이 PasswordEncoder 객체의 encode() 메서드를 사용해서 인코딩(암호화)한 뒤 저장
        //입력값도 마찬가지
        return NoOpPasswordEncoder.getInstance();
    }*/

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
