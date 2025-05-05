package com.rural.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rural.platform.entity.AnnualStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AnnualStatisticsMapper extends BaseMapper<AnnualStatistics> {
    @Select("SELECT " +
            "a.id, a.year, a.town_id, a.population, a.gdp, a.income, " +
            "a.primary_industry, a.secondary_industry, a.tertiary_industry, " +
            "a.created_at, a.updated_at, " +
            "t.id as 'townInfo.id', " +
            "t.name as 'townInfo.name', " +
            "t.district as 'townInfo.district', " +
            "t.population as 'townInfo.population', " +
            "t.gdp as 'townInfo.gdp', " +
            "t.income as 'townInfo.income', " +
            "t.primary_industry as 'townInfo.primaryIndustry', " +
            "t.secondary_industry as 'townInfo.secondaryIndustry', " +
            "t.tertiary_industry as 'townInfo.tertiaryIndustry', " +
            "t.description as 'townInfo.description', " +
            "t.features as 'townInfo.features', " +
            "t.address as 'townInfo.address', " +
            "t.phone as 'townInfo.phone', " +
            "t.email as 'townInfo.email', " +
            "t.created_at as 'townInfo.createdAt', " +
            "t.updated_at as 'townInfo.updatedAt' " +
            "FROM annual_statistics a " +
            "LEFT JOIN town_info t ON a.town_id = t.id " +
            "WHERE a.year = #{year}")
    List<AnnualStatistics> findByYear(Integer year);
} 