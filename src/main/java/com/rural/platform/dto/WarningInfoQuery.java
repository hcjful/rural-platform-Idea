package com.rural.platform.dto;

import lombok.Data;

@Data
public class WarningInfoQuery {
    private Integer currentPage = 1;
    private Integer pageSize = 10;
    private String keyword;
    private String type;
    private String status;
}
