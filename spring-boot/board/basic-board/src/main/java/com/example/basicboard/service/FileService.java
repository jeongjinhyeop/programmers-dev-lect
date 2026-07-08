package com.example.basicboard.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    // getBytes() + Files.write() 방식과 비교했을 때의 핵심 차이
    // (기존) byte[] bytes = file.getBytes(); Files.write(...);
    // 메모리 : getBytes()는 파일 "전체"를 byte[]로 힙 메모리에 올린다 -> 큰 파일/동시 업로드 시 OOM(Out Of Memory) 위험

    public String storeFile(MultipartFile file) {

        if( file == null || file.isEmpty()) return null;

        try  {
            File dir = new File(uploadDir).getAbsoluteFile();
            if (!dir.exists()) dir.mkdir();

            String storedFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(dir, storedFileName);
            file.transferTo(dest);

            return dest.getPath();
        }catch (Exception e){
            throw new IllegalStateException("파일 저장에 실패", e);
        }
    }
}
