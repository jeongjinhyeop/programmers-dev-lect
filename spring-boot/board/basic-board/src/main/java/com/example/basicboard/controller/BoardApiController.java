package com.example.basicboard.controller;

import com.example.basicboard.domain.entitiy.Board;
import com.example.basicboard.dto.BoardResponseDto;
import com.example.basicboard.service.BoardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BoardApiController {

    private BoardService boardService;

    @GetMapping
    public BoardResponseDto getBoardList(
            //아무것도 안보내면 1페이지
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ){
        List<Board> boards = boardService.getBoardList(page, size);

        //전체 게시글 수 가져오기
        int totalBoards = boardService.getTotalBoards();

        int totalPages = (int) Math.ceil((double) totalBoards /size);

        //마지막 페이지 여부
        boolean last = page >= totalPages;

        return BoardResponseDto.builder()
                .boards(boards)
                .last(last)
                .totalPages(totalPages)
                .build();
    }

    @PostMapping
    public void saveBoard(){

    }
}
