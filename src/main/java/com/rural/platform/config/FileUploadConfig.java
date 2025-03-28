package com.rural.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import javax.servlet.MultipartConfigElement;
import org.springframework.util.unit.DataSize;

@Configuration
public class FileUploadConfig {
    
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
    
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 设置单个文件最大大小为10MB
        factory.setMaxFileSize(DataSize.ofMegabytes(10));
        // 设置总上传数据最大大小为20MB
        factory.setMaxRequestSize(DataSize.ofMegabytes(20));
        return factory.createMultipartConfig();
    }
} 