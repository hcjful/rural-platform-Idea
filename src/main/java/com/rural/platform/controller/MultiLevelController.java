package com.rural.platform.controller;

import com.rural.platform.response.Result;
import com.rural.platform.service.MultiLevelService;
import com.rural.platform.vo.multilevel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/multilevel")
@RequiredArgsConstructor
public class MultiLevelController {
    private final MultiLevelService multiLevelService;

    @GetMapping("/overview/{year}")
    public Result<OverviewVO> getOverview(@PathVariable Integer year) {
        return Result.success(multiLevelService.getOverview(year));
    }

    @GetMapping("/population/{year}")
    public Result<PopulationVO> getPopulationDistribution(@PathVariable Integer year) {
        return Result.success(multiLevelService.getPopulationDistribution(year));
    }

    @GetMapping("/economic/{year}")
    public Result<EconomicVO> getEconomicIndicators(@PathVariable Integer year) {
        return Result.success(multiLevelService.getEconomicIndicators(year));
    }

    @GetMapping("/industry/{year}")
    public Result<List<IndustryVO>> getIndustryStructure(@PathVariable Integer year) {
        return Result.success(multiLevelService.getIndustryStructure(year));
    }

    @GetMapping("/trend")
    public Result<TrendVO> getDevelopmentTrend(
            @RequestParam Integer startYear,
            @RequestParam Integer endYear) {
        return Result.success(multiLevelService.getDevelopmentTrend(startYear, endYear));
    }

    @GetMapping("/town/{townId}")
    public Result<TownDetailVO> getTownDetail(@PathVariable String townId) {
        return Result.success(multiLevelService.getTownDetail(townId));
    }
} 