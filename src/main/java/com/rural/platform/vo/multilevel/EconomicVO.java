package com.rural.platform.vo.multilevel;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class EconomicVO {
    private List<String> towns;
    private List<BigDecimal> gdp;
    private List<BigDecimal> income;
} 