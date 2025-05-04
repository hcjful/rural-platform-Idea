package com.rural.platform.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductInfo {
    private Long id;
    private String name;
    private String image;
    private BigDecimal price;
} 