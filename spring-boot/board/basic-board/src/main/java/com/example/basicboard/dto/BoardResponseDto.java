package com.example.basicboard.dto;

import com.example.basicboard.domain.entitiy.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
public class BoardResponseDto {
    private List<Board> boards;
    private boolean last;
    private int totalPages;
}
