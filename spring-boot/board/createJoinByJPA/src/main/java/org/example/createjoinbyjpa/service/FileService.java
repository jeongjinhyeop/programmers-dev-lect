package org.example.createjoinbyjpa.service;

import org.example.createjoinbyjpa.exception.BoardNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

@Service
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;

        try {
            File dir = new File(uploadDir).getAbsoluteFile();
            if (!dir.exists()) dir.mkdir();

            String storedName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(dir, storedName);
            //transferTo 전의 "임시 파일"은 톰캣이 시스템 임시 폴더에 만들어 둔 것
            file.transferTo(dest);

            return dest.getPath();

        } catch (IOException e) {
            throw new IllegalStateException("파일 저장에 실패했습니다.", e);
        }
    }

    public void deleteFile(String filePath)   {
        if (filePath == null || filePath.isBlank()) return;
        File file = new File(filePath);
        if (file.exists()) file.delete();
    }

    public Resource downloadFile(String fileName){
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