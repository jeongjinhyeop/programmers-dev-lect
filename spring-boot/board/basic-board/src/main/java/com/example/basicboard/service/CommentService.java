package com.example.basicboard.service;

import com.example.basicboard.domain.entitiy.Board;
import com.example.basicboard.domain.entitiy.Comment;
import com.example.basicboard.domain.repository.BoardRepository;
import com.example.basicboard.domain.repository.CommentRepository;
import com.example.basicboard.dto.CommentWriteRequestDto;
import com.example.basicboard.exception.BoardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void addComment(Long boardId, CommentWriteRequestDto dto){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("게시글을 찾을 수 없습니다. id = " +  boardId));

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .userId(dto.getUserId())
                .created(LocalDateTime.now())
                .board(board)
                .build();

        commentRepository.save(comment);
    }
}
