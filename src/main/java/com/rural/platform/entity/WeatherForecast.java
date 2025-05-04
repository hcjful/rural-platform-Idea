package com.rural.platform.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class WeatherForecast {
    private Long id;
    private String cityCode;
    private LocalDate forecastDate;
    private BigDecimal highTemp;
    private BigDecimal lowTemp;
    private Integer precipitation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 