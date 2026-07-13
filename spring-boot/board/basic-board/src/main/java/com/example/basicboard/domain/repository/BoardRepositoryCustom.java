package com.example.basicboard.domain.repository;

import com.example.basicboard.dto.BoardListItemResponseDto;
import com.example.basicboard.dto.BoardSearchRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    Page<BoardListItemResponseDto> searchBoards(BoardSearchRequestDto dto, Pageable pageable);
}
