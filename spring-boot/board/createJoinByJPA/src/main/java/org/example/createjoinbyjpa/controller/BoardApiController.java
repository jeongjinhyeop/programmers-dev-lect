package org.example.createjoinbyjpa.controller;

import lombok.RequiredArgsConstructor;
import org.example.createjoinbyjpa.domain.entity.Board;
import org.example.createjoinbyjpa.dto.BoardListResponseDto;
import org.example.createjoinbyjpa.service.BoardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping
    public BoardListResponseDto getBoardList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<Board> boards = boardService.getBoardList(page, size);
        int totalBoards = boardService.getTotalBoards();

        // 전체 글 수 ÷ 페이지당 개수 → "올림" (21개/10 = 3페이지)
        int totalPages = (int) Math.ceil((double) totalBoards / size);
        boolean last = page >= totalPages;

        return BoardListResponseDto.builder()
                .boards(boards)
                .last(last)
                .totalPages(totalPages)
                .build();
    }
}
