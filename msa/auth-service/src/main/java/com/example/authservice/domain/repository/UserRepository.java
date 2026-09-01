package com.example.authservice.domain.repository;

import com.example.authservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

    List<User> findByUserIdIn(List<String> userIds);

    boolean existsByUserId(String userId);
}
