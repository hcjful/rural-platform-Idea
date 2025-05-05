package com.rural.platform.vo.multilevel;

import lombok.Data;
import java.util.List;

@Data
public class PopulationVO {
    private String name;
    private Integer value;
    private List<PopulationVO> children;
} 