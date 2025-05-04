package com.rural.platform.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class WeatherData {
    private Long id;
    private String cityCode;
    private BigDecimal temperature;
    private BigDecimal feelsLike;
    private String description;
    private String weatherType;
    private BigDecimal windSpeed;
    private Integer humidity;
    private String sunrise;
    private String sunset;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 