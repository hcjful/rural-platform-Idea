package com.rural.platform.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LogisticsTrack {
    private Long id;
    private Long logisticsId;
    private String location;
    private String description;
    private LocalDateTime trackTime;
    private LocalDateTime createdAt;
} 