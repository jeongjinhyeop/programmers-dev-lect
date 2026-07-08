package com.example.basicboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class BoardDetailResponseDto {
    private  String title;
    private  String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDate created;
    private String userId;
    private String filePath;
}
