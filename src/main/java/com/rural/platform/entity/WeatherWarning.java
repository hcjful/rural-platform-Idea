package com.rural.platform.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WeatherWarning {
    private Long id;
    private String cityCode;
    private String type;
    private String content;
    private LocalDateTime time;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 