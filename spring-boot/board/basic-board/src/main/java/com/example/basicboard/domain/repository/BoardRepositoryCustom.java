package com.example.basicboard.domain.repository;

import com.example.basicboard.domain.entitiy.Board;
import com.example.basicboard.dto.BoardListItemResponseDto;
import com.example.basicboard.dto.BoardSearchRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BoardRepositoryCustom {

    Page<BoardListItemResponseDto> searchBoards(BoardSearchRequestDto dto, Pageable pageable);
    Optional<Board> findWithComment (Long id);
}
