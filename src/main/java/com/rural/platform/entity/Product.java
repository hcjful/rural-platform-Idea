package com.rural.platform.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Product {
    private Long id;
    private String name;
    private BigDecimal price;
    private String image;
    private String description;
    private String category;
    private String origin;
    private Integer stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 