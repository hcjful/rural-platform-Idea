package com.rural.platform.utils;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUploadUtils {
    private static final String UPLOAD_PATH = "/uploads/";
    
    public static String upload(MultipartFile file, String directory) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + suffix;
        String uploadDir = UPLOAD_PATH + directory;
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dest = new File(uploadDir + "/" + fileName);
        file.transferTo(dest);
        return directory + "/" + fileName;
    }
}
