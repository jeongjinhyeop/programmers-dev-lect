package com.example.webservice.dto;

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
    private String userId;
    private MultipartFile file;
    private boolean fileFlag;
}
