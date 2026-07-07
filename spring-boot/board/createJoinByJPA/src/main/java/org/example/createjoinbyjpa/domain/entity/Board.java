package org.example.createjoinbyjpa.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "board")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT") // 본문은 길 수 있어 TEXT
    private String content;

    @Column(nullable = false, length = 30)
    private String userId;

    @Column(length = 255)
    private String filePath; // 첨부파일 경로 - 없을 수 있어 nullable

    // 안 붙이면 2026-06-24T08:43:00 처럼 'T'가 붙는다 → pattern으로 사람 친화적 표기
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime created;
}
