package com.rural.platform.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AirQuality {
    private Long id;
    private String cityCode;
    private Integer aqi;
    private String level;
    private BigDecimal pm25;
    private BigDecimal pm10;
    private BigDecimal no2;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 