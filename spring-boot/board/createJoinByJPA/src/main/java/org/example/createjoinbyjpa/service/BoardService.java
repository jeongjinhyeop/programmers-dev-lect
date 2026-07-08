package org.example.createjoinbyjpa.service;

import lombok.RequiredArgsConstructor;
import org.example.createjoinbyjpa.domain.entity.Board;
import org.example.createjoinbyjpa.domain.repository.BoardRepository;
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
        String filePath = storeFile(file);   // 첨부파일 없으면 null 반환

        Board board = Board.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .filePath(filePath)
                .created(LocalDateTime.now())
                .build();

        boardRepository.save(board);
    }

    private String storeFile (MultipartFile file) {
        if(file == null || file.isEmpty()) return null;

        try {
            File dir = new File(uploadDir).getAbsoluteFile();
            if(!dir.exists()) dir.mkdir();

            String storedName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(dir,storedName);
            //transferTo 전의 "임시 파일"은 톰캣이 시스템 임시 폴더에 만들어 둔 것
            file.transferTo(dest);

            return dest.getPath();

        } catch (IOException e) {
            throw new IllegalStateException("파일 저장에 실패했습니다.", e);
        }
    }

    public Resource downloadFile(String fileName) {
        try {
            File file = new File(new File(uploadDir).getAbsoluteFile(), fileName);
            Resource resource = new UrlResource(file.toURI());
            if (!resource.exists() || !resource.isReadable()) {
                throw new BoardNotFoundException("파일을 찾을 수 없습니다. fileName=" + fileName);
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new IllegalStateException("파일 경로가 잘못되었습니다.", e);
        }
    }
}
