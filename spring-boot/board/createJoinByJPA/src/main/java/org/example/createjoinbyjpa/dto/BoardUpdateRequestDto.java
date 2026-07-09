package org.example.createjoinbyjpa.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class BoardUpdateRequestDto {
    private String title;
    private String content;
    private boolean fileFlag;    // 파일을 건드렸는지(교체/제거) 여부
    private MultipartFile file;  // 새로 올린 파일 (교체할 때만 값이 있음)
}

