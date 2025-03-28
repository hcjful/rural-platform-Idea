package com.rural.platform.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Review {
    private Long id;
    private Long productId;
    private Long userId;
    private Long orderId;
    private Integer rating; // 1-5星评分
    private String content;
    private String images; // 评价图片，多个图片用逗号分隔
    private Boolean isAnonymous;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 