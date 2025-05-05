package com.rural.platform.service;

import com.rural.platform.vo.multilevel.*;
import java.util.List;

public interface MultiLevelService {
    /**
     * 获取乡镇概览数据
     */
    OverviewVO getOverview(Integer year);

    /**
     * 获取人口分布数据
     */
    PopulationVO getPopulationDistribution(Integer year);

    /**
     * 获取经济指标数据
     */
    EconomicVO getEconomicIndicators(Integer year);

    /**
     * 获取产业结构数据
     */
    List<IndustryVO> getIndustryStructure(Integer year);

    /**
     * 获取发展趋势数据
     */
    TrendVO getDevelopmentTrend(Integer startYear, Integer endYear);

    /**
     * 获取乡镇详细信息
     */
    TownDetailVO getTownDetail(String townId);
} 