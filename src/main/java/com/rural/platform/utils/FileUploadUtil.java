package com.rural.platform.utils;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class FileUploadUtil {
    
    private static final String UPLOAD_DIR = "uploads";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    
    public static String uploadFile(MultipartFile file, String type) throws IOException {
        // 创建上传目录
        String datePath = LocalDateTime.now().format(DATE_FORMATTER);
        String uploadPath = UPLOAD_DIR + "/" + type + "/" + datePath;
        Path directory = Paths.get(uploadPath);
        Files.createDirectories(directory);
        
        // 生成文件名
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = UUID.randomUUID().toString() + extension;
        
        // 保存文件
        Path filePath = directory.resolve(filename);
        Files.copy(file.getInputStream(), filePath);
        
        // 返回文件访问路径
        return "/" + uploadPath + "/" + filename;
    }
    
    public static void deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath.substring(1)); // 去掉开头的斜杠
            Files.deleteIfExists(path);
        } catch (IOException e) {
            // 记录日志但不抛出异常
            e.printStackTrace();
        }
    }
} 