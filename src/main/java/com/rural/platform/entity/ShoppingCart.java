package com.rural.platform.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ShoppingCart {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Product product;
} 