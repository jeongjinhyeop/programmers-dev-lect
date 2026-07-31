package com.example.basicboard.service;

import com.example.basicboard.config.security.CustomUserDetails;
import com.example.basicboard.controller.BoardUpdateRequestDto;
import com.example.basicboard.domain.entitiy.Board;
import com.example.basicboard.domain.repository.BoardRepository;
import com.example.basicboard.dto.BoardAuthorStatsResponseDto;
import com.example.basicboard.dto.BoardDeleteRequestDto;
import com.example.basicboard.dto.BoardListItemResponseDto;
import com.example.basicboard.dto.BoardSearchRequestDto;
import com.example.basicboard.exception.BoardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import org.springframework.security.access.AccessDeniedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class BoardService {
    private final FileService fileService;
    private final BoardRepository boardRepository;

    public List<Board> getBoardList(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        // * findAll(pageable).getContent()의 getContent()란?
        // findAll(pageable)의 반환 타입은 Page<Board>다
        // Page가 제공하는 것들
        // - getContent() -> List<Board> : "이번 페이지의 게시글 목록"
        // - getTotalElements() -> long : 전체 게시글 수
        // - getTotalPages() -> int : 전체 페이지 수
        // - isLast() -> boolean : 마지막 페이지 여부
        // 주의 : getContent()의 'content'는 Board 엔티티의 content가 아니다.
        return boardRepository.findAll(pageable).getContent();
    }
    @Transactional
    public int getTotalBoards() {
        return (int) boardRepository.count();
    }

    public void saveBoard (String userId, String title, String content, MultipartFile file){
        String filePath = fileService.storeFile(file);

        boardRepository.save(
                Board.builder()
                        .userId(userId)
                        .title(title)
                        .content(content)
                        .filePath(filePath)
                        .created(LocalDateTime.now())
                        .build()
        );
    }

//    public Board getBoardDetail(long id) {
//        return boardRepository.findById(id)
//                .orElseThrow( () -> new BoardNotFoundException("[BOARD] 게시글을 찾을 수 없습니다. id : " + id));
//    }
public Board getBoardDetail(long id, CustomUserDetails userDetails) {
    // 1. 게시글 존재 조회
    Board board = boardRepository.findById(id)
            .orElseThrow(() -> new BoardNotFoundException("[BOARD] 게시글을 찾을 수 없습니다. id : " + id));

    // 2. 로그인 사용자 ID 및 ADMIN 권한 확인
    String currentUserId = userDetails.getUsername();
    boolean isAdmin = userDetails.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    // 3. 인가 검사: 어드민도 아니고 본인 글도 아니면 거부
    if (!isAdmin && !board.getUserId().equals(currentUserId)) {
        throw new AccessDeniedException("본인의 게시글만 조회할 수 있습니다.");
    }

    return board;
}
    @Transactional
    public void updateBoard(long id, BoardUpdateRequestDto dto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(
                        () -> new BoardNotFoundException("[BOARD] 수정할 게시글을 찾을 수 없습니다. id : " + id)
                );

        String filePath = board.getFilePath();
        if ( dto.isFileFlag() ) { // 파일 변경이 있었을 경우
            fileService.deleteFile(filePath); // 기존 파일 삭제
            filePath = fileService.storeFile(dto.getFile()); // 새 파일 저장
        }

        board.update( dto.getTitle(), dto.getContent(), filePath );
    }


    @Transactional
    public void deleteBoard(long id, BoardDeleteRequestDto dto) {

        if ( !boardRepository.existsById(id) ) {
            throw new BoardNotFoundException("[BOARD] 삭제할 게시글을 찾을 수 없습니다. id : " + id);
        }

        boardRepository.deleteById(id);
        fileService.deleteFile(dto.getFilePath());
    }

    public Page<BoardListItemResponseDto> searchBoards(BoardSearchRequestDto dto, Pageable pageable) {
        return boardRepository.searchBoards(dto, pageable);
    }

//    public Board getBoardWithComments(long id) {
//        return boardRepository.findWithComment(id)
//                .orElseThrow(
//                        () -> new BoardNotFoundException("게시글을 찾을 수 없습니다. id = " + id)
//                );
//    }

    public Board getBoardWithComments(long id, CustomUserDetails userDetails) {
        // 1. 게시글 및 댓글 데이터 조회
        Board board = boardRepository.findWithComment(id)
                .orElseThrow(
                        () -> new BoardNotFoundException("게시글을 찾을 수 없습니다. id = " + id)
                );

        // 2. 로그인한 유저 ID 및 관리자 여부 확인
        String currentUserId = userDetails.getUsername();
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // 3. 인가(Authorization) 검사
        if (!isAdmin && !board.getUserId().equals(currentUserId)) {
            throw new AccessDeniedException("본인의 게시글만 조회할 수 있습니다.");
        }

        return board;
    }

    public List<BoardAuthorStatsResponseDto> getAuthorStats(long minCount) {
        return boardRepository.countBoardsByAuthor(minCount);
    }

}
