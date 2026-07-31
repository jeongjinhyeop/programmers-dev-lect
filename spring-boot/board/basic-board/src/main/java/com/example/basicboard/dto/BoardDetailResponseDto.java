package com.example.basicboard.dto;

import com.example.basicboard.domain.entitiy.Board;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class BoardDetailResponseDto {
    private  String title;
    private  String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime created;
    private String userId;
    private String filePath;

    public static BoardDetailResponseDto from(Board board) {
        return BoardDetailResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .created(board.getCreated())
                .userId(board.getUserId())
                .filePath(board.getFilePath())
                .build();
    }

}
