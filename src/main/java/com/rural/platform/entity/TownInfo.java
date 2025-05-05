package com.rural.platform.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.rural.platform.handler.JsonTypeHandler;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("town_info")
public class TownInfo {
    @TableId
    private String id;
    
    private String name;
    
    private String district;
    
    private Integer population;
    
    private BigDecimal gdp;
    
    private BigDecimal income;
    
    @TableField("primary_industry")
    private BigDecimal primaryIndustry;
    
    @TableField("secondary_industry")
    private BigDecimal secondaryIndustry;
    
    @TableField("tertiary_industry")
    private BigDecimal tertiaryIndustry;
    
    private String description;
    
    @TableField(typeHandler = JsonTypeHandler.class)
    private List<String> features;
    
    private String address;
    
    private String phone;
    
    private String email;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
} 