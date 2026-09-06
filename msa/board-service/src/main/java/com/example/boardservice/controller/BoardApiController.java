package com.example.boardservice.controller;

import com.example.boardservice.config.security.CustomUserDetails;
import com.example.boardservice.domain.entity.Board;
import com.example.boardservice.dto.*;
import com.example.boardservice.mapper.BoardMapper;
import com.example.boardservice.service.BoardService;
import com.example.boardservice.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardApiController {

    private final BoardService boardService;
    private final BoardMapper boardMapper;
    private final FileService fileService;

    @GetMapping("/search")
    public Page<BoardListItemResponseDto> searchBoards(
            @ModelAttribute BoardSearchRequestDto dto,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return boardService.searchBoards(dto, PageRequest.of(page - 1, size));
    }

    @GetMapping("/{id}/with-comments")
    public BoardWithCommentsResponseDto getBoardWithComments(@PathVariable("id") long id ) {
        Board board = boardService.getBoardWithComments(id);
        return boardMapper.toBoardWithCommentsResponseDto(board);
    }

    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public void saveBoard(@ModelAttribute BoardWriteRequestDto dto) {
        boardService.saveBoard(dto.getUserId(), dto.getTitle(), dto.getContent(), dto.getFile());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDetailResponseDto> getBoardDetail(
            @PathVariable long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Board board = boardService.getBoardDetail(id, userDetails);
        return ResponseEntity.ok(BoardDetailResponseDto.from(board));
    }

    @Operation(summary = "게시글 수정",
            description = "경로의 id 게시글을 수정한다. 파일 교체가 가능하도록 multipart/form-data 로 받는다.")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateBoard(
            @PathVariable long id,
            @ModelAttribute BoardUpdateRequestDto dto
    ) {
        boardService.updateBoard(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(
            @PathVariable long id,
            @RequestBody BoardDeleteRequestDto dto
    ) {
        boardService.deleteBoard(id, dto);
    }

    @GetMapping("/file/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource = fileService.downloadFile(fileName);

        // * 한글 파일명 인코딩
        // HTTP 헤더 값에는 원칙적으로 ASCII만 안전하게 담을 수 있다.
        // -> "이력서.pdf"같은 한글/공백을 그대로 넣으면 깨지거나 잘린다.
        // 그래서 파일명을 URL 인코딩해서 넣는다.
        //   - URLEncoder 는 공백을 '+' 로 바꾸는데, 파일명에선 '+' 가 그대로 보이면 곤란하므로 %20 으로 치환한다
        String encodedFileName = URLEncoder.encode(resource.getFilename(), StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");

        // * contentType(MediaType.APPLICATION_OCTET_STREAM) => 힌트
        // 무슨 파일인지 특정하지 않은 순수 바이너리 라는 뜻
        // 브라우저가 열 방법을 몰라 저장 쪽으로 기울게 하는 '힌트'일 뿐, 다운로드 확정하지 못한다.
        // (확장자 등에 따라 브라우저가 그냥 열어버릴 수도 있다.)
        // * .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName)
        // attachment는 인라인으로 열지말고 무조건 첨부(다운로드)하라는 확실한 지시
        // filename*(별표) 는 인코딩을 명시하는 최신 문법으로 "저장될 기본 파일명"을 정한다.
        // 이게 없으면 URL 끝의 UUID 붙음 이름으로 저장돼 버린다. 그래서 원본 이름으로 저장되게 넣는 것
        // utf8 뒤에 '' : 언어필드 생략 (ex utf8'ko') 기능상 의미가 있다기 보단 명시적으로 알려주는 역할
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName)
                .body(resource);
    }
}