package com.rural.platform.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Favorite {
    private Long id;
    private Long userId;
    private Long productId;
    private LocalDateTime createdAt;
} 