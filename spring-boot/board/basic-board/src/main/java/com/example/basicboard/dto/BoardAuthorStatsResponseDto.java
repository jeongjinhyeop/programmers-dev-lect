package com.example.basicboard.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class BoardAuthorStatsResponseDto {

    private final String userId;
    private final String userName;
    private final Long boardCount;

    //Projection.constructor과 비교되는 방
    @QueryProjection
    public BoardAuthorStatsResponseDto(String userId, String userName, Long boardCount) {
        this.userId = userId;
        this.userName = userName;
        this.boardCount = boardCount;
    }
}
