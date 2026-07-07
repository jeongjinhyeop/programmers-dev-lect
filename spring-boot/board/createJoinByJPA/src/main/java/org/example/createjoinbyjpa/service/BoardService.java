package org.example.createjoinbyjpa.service;

import lombok.RequiredArgsConstructor;
import org.example.createjoinbyjpa.domain.entity.Board;
import org.example.createjoinbyjpa.domain.repository.BoardRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<Board> getBoardList(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        return boardRepository.findAll(pageable).getContent(); // 이번 페이지의 글 목록
    }

    @Transactional(readOnly = true)
    public int getTotalBoards() {
        return (int) boardRepository.count();
    }
}
