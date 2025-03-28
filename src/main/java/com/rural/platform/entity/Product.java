package com.rural.platform.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Product {
    private Long id;
    private String name;
    private String category;
    private BigDecimal price;
    private String origin;
    private String description;
    private String image;
    private Integer stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 