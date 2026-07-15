package org.example.createjoinbyjpa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.example.createjoinbyjpa.domain.entity.Board;
import org.example.createjoinbyjpa.dto.*;
import org.example.createjoinbyjpa.mapper.BoardMapper;
import org.example.createjoinbyjpa.service.BoardService;
import org.example.createjoinbyjpa.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;
    private final FileService fileService;
    private final BoardMapper boardMapper;

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

    @GetMapping("/{id}")
    public BoardDetailResponseDto getBoardDetail(@PathVariable long id) {
        Board board = boardService.getBoardDetail(id);

        return BoardDetailResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .created(board.getCreated())
                .userId(board.getUserId())
                .filePath(board.getFilePath())
                .build();
    }
    @PostMapping
    public void saveArticle(@ModelAttribute BoardWriteRequestDto request) {
        boardService.saveArticle(
                request.getUserId(),
                request.getTitle(),
                request.getContent(),
                request.getFile()
        );
    }

    @GetMapping("/file/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource = fileService.downloadFile(fileName);

        String encoded = URLEncoder.encode(resource.getFilename(), StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename*=UTF-8''" + encoded)    // 저장
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable long id, @RequestBody BoardDeleteRequestDto request) {
        boardService.deleteArticle(id, request);
    }

    @PutMapping("/{id}")
    public void updateArticle(@PathVariable long id, @ModelAttribute BoardUpdateRequestDto request) {
        boardService.updateArticle(id, request);
    }

    @GetMapping("/{id}/with-comments")
    public BoardWithCommentsResponseDto getBoardWithComments(
            @Parameter(description = "조회할 게시글 id", example = "1")
            @PathVariable long id
    ) {
        Board board = boardService.getBoardWithComments(id);

        return boardMapper.toBoardWithCommentResponseDto(board);
    }

    @Operation(summary = "작성자별 게시글 수 통계",
        description = "11"
    )
    @GetMapping("/stats/authors")
    public  List<BoardAuthorStatsResponseDto> getAuthorStats(
            @Parameter(description = "최소 게시글 수 (이 값 이상은 쓴 작성자만)", example = "1")
            @RequestParam(defaultValue = "1") long minCount
    ){

        return  boardService.getAuthorStats(minCount);
    }
}
