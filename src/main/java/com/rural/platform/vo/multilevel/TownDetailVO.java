package com.rural.platform.vo.multilevel;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class TownDetailVO {
    private String id;
    private String name;
    private Integer population;
    private BigDecimal gdp;
    private BigDecimal income;
    private IndustryStructure industries;
    private String description;
    private List<String> features;
    private Contact contact;

    @Data
    public static class IndustryStructure {
        private BigDecimal primary;
        private BigDecimal secondary;
        private BigDecimal tertiary;
    }

    @Data
    public static class Contact {
        private String address;
        private String phone;
        private String email;
    }
} 