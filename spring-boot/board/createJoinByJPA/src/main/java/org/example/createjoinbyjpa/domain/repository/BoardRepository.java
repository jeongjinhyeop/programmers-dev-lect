package org.example.createjoinbyjpa.domain.repository;

import org.example.createjoinbyjpa.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
