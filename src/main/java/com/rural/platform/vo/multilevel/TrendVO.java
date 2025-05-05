package com.rural.platform.vo.multilevel;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class TrendVO {
    private List<String> years;
    private List<BigDecimal> gdp;
    private List<BigDecimal> population;
} 