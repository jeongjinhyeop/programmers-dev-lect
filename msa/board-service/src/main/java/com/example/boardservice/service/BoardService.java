package com.example.boardservice.service;

import com.example.boardservice.client.AuthClient;
import com.example.boardservice.config.security.CustomUserDetails;
import com.example.boardservice.domain.entity.Board;
import com.example.boardservice.domain.repository.BoardRepository;
import com.example.boardservice.domain.repository.CommentRepository;
import com.example.boardservice.dto.*;
import com.example.boardservice.exception.BoardNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final AuthClient authClient;
    private final FileService fileService;
    private final CommentRepository commentRepository;


    public Page<BoardListItemResponseDto> searchBoards(BoardSearchRequestDto dto, Pageable pageable) {

        // searchBoards 게시글들 가져오기
        Page<BoardListItemResponseDto> page = boardRepository.searchBoards(dto, pageable);

        // boardRepository에서 가져온 ID추려서 auth-service로 요청해서 userName들 받아오기
        List<UserNameResponseDto> userNameResponseDtos = fetchNames(
                page.getContent().stream().map(BoardListItemResponseDto::getUserId).distinct().toList()
        );

        return page.map( item -> new BoardListItemResponseDto(
                item.getId(),
                item.getTitle(),
                item.getUserId(),
                userNameOf(userNameResponseDtos, item.getUserId()),
                item.getCommentCount(),
                item.getCreated()
        ));
    }

    public Board getBoardWithComments(long id) {
        return boardRepository.findWithComment(id)
                .orElseThrow(() -> new BoardNotFoundException("게시글을 찾을 수 없습니다." + id));
    }

    // auth가 죽어도 게시판 조회 자체는 살아야 하므로(부분 실패 허용)
    // 실패 시 빈 목록을 돌려 이름 없이 응답한다. -> 장애 전파를 끊는다.
    private List<UserNameResponseDto> fetchNames(List<String> userIds) {

        if ( userIds == null || userIds.isEmpty() ) {
            return List.of();
        }

        try {
            return authClient.getUserNames(userIds);
        } catch (Exception e) {
            log.warn("[작성자 이름 조회 실패] auth-service 호출 불가 — userId로 대체 표시. {}", e.getMessage());
            return List.of();
        }

    }

    private String userNameOf(List<UserNameResponseDto> userNames, String userId) {
        return userNames.stream()
                .filter( userName -> userName.getUserId().equals(userId) )
                .map(UserNameResponseDto::getUserName)
                .findFirst()
                .orElse(null);
    }

    public void saveBoard(String userId, String title, String content, MultipartFile file) {
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

    public void deleteBoard(long id, BoardDeleteRequestDto dto) {

        if ( !boardRepository.existsById(id) ) {
            throw new BoardNotFoundException("[BOARD] 삭제할 게시글을 찾을 수 없습니다. id = " + id);
        }

        // comment
        commentRepository.deleteByBoardId(id);
        // board
        boardRepository.deleteById(id);
        // file
        fileService.deleteFile(dto.getFilePath());

    }
}