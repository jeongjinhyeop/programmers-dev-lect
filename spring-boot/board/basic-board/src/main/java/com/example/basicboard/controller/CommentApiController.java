package com.example.basicboard.controller;

import com.example.basicboard.dto.CommentWriteRequestDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "댓글 API", description = "게시글에 댓글 관련")
@RestController
@RequestMapping("/api/boards/{boardId}/comments")
public class CommentApiController {
    @PostMapping
    public void addComment(
            @Parameter(description = "댓글을 달 게시글 id", example = "1")
            @PathVariable long boardId,
            @RequestBody CommentWriteRequestDto dto
            ){

    }
}
