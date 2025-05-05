package com.rural.platform.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("annual_statistics")
public class AnnualStatistics {
    @TableId
    private String id;
    
    private Integer year;
    
    @TableField("town_id")
    private String townId;
    
    private Integer population;
    
    private BigDecimal gdp;
    
    private BigDecimal income;
    
    @TableField("primary_industry")
    private BigDecimal primaryIndustry;
    
    @TableField("secondary_industry")
    private BigDecimal secondaryIndustry;
    
    @TableField("tertiary_industry")
    private BigDecimal tertiaryIndustry;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    
    @TableField(exist = false)
    private TownInfo townInfo;
} 