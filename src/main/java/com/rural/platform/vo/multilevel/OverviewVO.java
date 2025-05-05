package com.rural.platform.vo.multilevel;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OverviewVO {
    private Integer townCount;
    private Integer totalPopulation;
    private BigDecimal gdp;
    private BigDecimal growthRate;
} 