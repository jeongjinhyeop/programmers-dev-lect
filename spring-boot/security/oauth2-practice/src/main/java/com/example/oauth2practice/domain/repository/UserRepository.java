package com.example.oauth2practice.domain.repository;


import com.example.oauth2practice.config.oauth2.AuthProvider;
import com.example.oauth2practice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserId(String userId);

    boolean existsByUserId(String userId);

    Optional<User> findByProviderIdAndProvider(String providerId, AuthProvider provider);
}
