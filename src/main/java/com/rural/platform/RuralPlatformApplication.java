package com.rural.platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@MapperScan(basePackages = {"com.rural.platform.mapper", "com.rural.platform.utils"})
@SpringBootApplication
public class RuralPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuralPlatformApplication.class, args);
    }

}
