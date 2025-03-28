package com.rural.platform.entity;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderDetail {
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
} 