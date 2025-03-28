package com.rural.platform.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Logistics {
    private Long id;
    private Long orderId;
    private String trackingNo;
    private String carrier; // 快递公司
    private String status; // PENDING, SHIPPING, DELIVERED
    private String currentLocation;
    private LocalDateTime estimatedDeliveryTime;
    private LocalDateTime deliveredAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 