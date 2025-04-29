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
    private OrderProductInfo product;  // 关联的商品信息

    private Product name;
    private Product image;
}