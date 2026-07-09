package org.example.createjoinbyjpa.service;

import lombok.RequiredArgsConstructor;
import org.example.createjoinbyjpa.domain.entity.Board;
import org.example.createjoinbyjpa.domain.repository.BoardRepository;
import org.example.createjoinbyjpa.dto.BoardDeleteRequestDto;
import org.example.createjoinbyjpa.dto.BoardUpdateRequestDto;
import org.example.createjoinbyjpa.exception.BoardNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final FileService fileService;
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Transactional(readOnly = true)
    public List<Board> getBoardList(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        return boardRepository.findAll(pageable).getContent(); // 이번 페이지의 글 목록
    }

    @Transactional(readOnly = true)
    public int getTotalBoards() {
        return (int) boardRepository.count();
    }

    @Transactional(readOnly = true)
    public Board getBoardDetail(Long id){
        return boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("게시글을 찾을 수 없습니다. id=" + id));
    }

    @Transactional
    public void saveArticle(String userId, String title, String content, MultipartFile file) {
        String filePath = fileService.storeFile(file);   // 첨부파일 없으면 null 반환

        Board board = Board.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .filePath(filePath)
                .created(LocalDateTime.now())
                .build();

        boardRepository.save(board);
    }

    @Transactional
    public void deleteArticle(Long id, BoardDeleteRequestDto request) {
        if (!boardRepository.existsById(id)) {
            throw new BoardNotFoundException("게시글을 찾을 수 없습니다. id=" + id);
        }
        boardRepository.deleteById(id);        // ① DB 먼저
        deleteFile(request.getFilePath());     // ② 파일 나중 (Step 5에서 FileService로 옮김)
    }

    // 파일 삭제 (첨부 없던 글이면 건너뜀)
    private void deleteFile(String filePath) {

    }

    @Transactional
    public void updateArticle(Long id, BoardUpdateRequestDto request) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("게시글을 찾을 수 없습니다. id=" + id));

        String filePath = board.getFilePath();   // 기본값: 기존 파일 유지
        if (request.isFileFlag()) {              // 파일을 건드렸다면
            deleteFile(board.getFilePath());     // 기존 파일 삭제
            filePath = fileService.storeFile(request.getFile()); // 새 파일 저장 (없으면 null = 제거)
        }
        board.update(request.getTitle(), request.getContent(), filePath); // 변경 감지로 UPDATE
    }

}
