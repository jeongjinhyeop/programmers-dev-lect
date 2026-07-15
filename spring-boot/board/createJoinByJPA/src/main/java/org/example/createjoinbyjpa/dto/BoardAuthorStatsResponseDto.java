package org.example.createjoinbyjpa.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class BoardAuthorStatsResponseDto {
    private final String userId;
    private final String userName;
    private final Long boardCount;

    @QueryProjection
    public BoardAuthorStatsResponseDto(String userId, String userName, Long boardCount) {
        this.userId = userId;
        this.userName = userName;
        this.boardCount = boardCount;
    }
}
