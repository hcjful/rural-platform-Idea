package com.rural.platform.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Payment {
    private Long id;
    private Long orderId;
    private String paymentNo;
    private BigDecimal amount;
    private String paymentMethod; // ALIPAY, WECHAT, BANK_TRANSFER
    private String status; // PENDING, SUCCESS, FAILED
    private String transactionId; // 第三方支付平台的交易ID
    private LocalDateTime paidAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 