package com.example.basicboard.domain.repository;

import com.example.basicboard.domain.entitiy.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
