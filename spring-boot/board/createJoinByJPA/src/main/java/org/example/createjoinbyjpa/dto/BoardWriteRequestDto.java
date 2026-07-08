package org.example.createjoinbyjpa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter //@ModelAttribute로 받으려면 setter+기본생성자가 필요(JavaBean)
@NoArgsConstructor
public class BoardWriteRequestDto {
    private String title;
    private String content;
    private String userId;
    private MultipartFile file;
}
