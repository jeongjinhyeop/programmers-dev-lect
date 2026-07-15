package org.example.createjoinbyjpa.domain.repository;

import org.example.createjoinbyjpa.domain.entity.Board;
import org.example.createjoinbyjpa.dto.BoardListItemResponseDto;
import org.example.createjoinbyjpa.dto.BoardSearchRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BoardRepositoryCustom {
    Page<BoardListItemResponseDto> searchBoards(BoardSearchRequestDto condition, Pageable pageable);
    Optional<Board> findWithComment (Long id);
}
