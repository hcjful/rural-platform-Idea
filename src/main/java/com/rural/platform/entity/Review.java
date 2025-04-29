package com.rural.platform.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private Long id;
    private Long productId;
    private Long userId;
    private Long orderId;
    private Integer rating; // 1-5星评分
    private String content;
    private String images; // 评论图片，多个图片URL用逗号分隔
    private LocalDateTime createdAt;

    public void setImages(String images) {
        this.images = images;
    }

    public String getImages() {
        return this.images;
    }
} 