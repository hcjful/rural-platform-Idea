package com.rural.platform.service.impl;

import com.rural.platform.entity.AnnualStatistics;
import com.rural.platform.entity.TownInfo;
import com.rural.platform.mapper.AnnualStatisticsMapper;
import com.rural.platform.mapper.TownInfoMapper;
import com.rural.platform.service.MultiLevelService;
import com.rural.platform.vo.multilevel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MultiLevelServiceImpl implements MultiLevelService {
    private final TownInfoMapper townInfoMapper;
    private final AnnualStatisticsMapper annualStatisticsMapper;

    @Override
    public OverviewVO getOverview(Integer year) {
        List<AnnualStatistics> currentYearStats = annualStatisticsMapper.findByYear(year);
        List<AnnualStatistics> lastYearStats = annualStatisticsMapper.findByYear(year - 1);
        
        // 如果当年数据为空，返回空对象
        if (currentYearStats == null || currentYearStats.isEmpty()) {
            OverviewVO emptyOverview = new OverviewVO();
            emptyOverview.setTownCount(0);
            emptyOverview.setTotalPopulation(0);
            emptyOverview.setGdp(BigDecimal.ZERO);
            emptyOverview.setGrowthRate(BigDecimal.ZERO);
            return emptyOverview;
        }

        BigDecimal currentYearGdp = currentYearStats.stream()
                .map(stat -> stat.getGdp() != null ? stat.getGdp() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal lastYearGdp = (lastYearStats != null && !lastYearStats.isEmpty()) ?
                lastYearStats.stream()
                        .map(stat -> stat.getGdp() != null ? stat.getGdp() : BigDecimal.ZERO)
                        .reduce(BigDecimal.ZERO, BigDecimal::add) :
                BigDecimal.ZERO;

        BigDecimal growthRate = lastYearGdp.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO :
                currentYearGdp.subtract(lastYearGdp)
                        .divide(lastYearGdp, 2, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal(100));

        OverviewVO overview = new OverviewVO();
        overview.setTownCount(currentYearStats.size());
        overview.setTotalPopulation(currentYearStats.stream()
                .mapToInt(stat -> stat.getPopulation() != null ? stat.getPopulation() : 0)
                .sum());
        overview.setGdp(currentYearGdp != null ? currentYearGdp : BigDecimal.ZERO);
        overview.setGrowthRate(growthRate != null ? growthRate : BigDecimal.ZERO);

        return overview;
    }

    @Override
    public PopulationVO getPopulationDistribution(Integer year) {
        List<AnnualStatistics> statistics = annualStatisticsMapper.findByYear(year);
        Map<String, List<AnnualStatistics>> districtMap = statistics.stream()
                .filter(s -> s.getTownInfo() != null && s.getTownInfo().getDistrict() != null)
                .collect(Collectors.groupingBy(s -> s.getTownInfo().getDistrict()));

        PopulationVO root = new PopulationVO();
        root.setName("全区");
        root.setValue(statistics.stream()
                .mapToInt(AnnualStatistics::getPopulation)
                .sum());

        List<PopulationVO> districts = new ArrayList<>();
        districtMap.forEach((district, stats) -> {
            PopulationVO districtVO = new PopulationVO();
            districtVO.setName(district);
            districtVO.setValue(stats.stream()
                    .mapToInt(AnnualStatistics::getPopulation)
                    .sum());

            List<PopulationVO> towns = stats.stream()
                    .map(stat -> {
                        PopulationVO townVO = new PopulationVO();
                        townVO.setName(stat.getTownInfo().getName());
                        townVO.setValue(stat.getPopulation());
                        return townVO;
                    })
                    .collect(Collectors.toList());

            districtVO.setChildren(towns);
            districts.add(districtVO);
        });

        root.setChildren(districts);
        return root;
    }

    @Override
    public EconomicVO getEconomicIndicators(Integer year) {
        List<AnnualStatistics> statistics = annualStatisticsMapper.findByYear(year);
        if (statistics == null || statistics.isEmpty()) {
            return new EconomicVO(); // 返回空对象而不是 null
        }
        
        // 先过滤掉无效数据
        List<AnnualStatistics> validStats = statistics.stream()
                .filter(stat -> stat.getTownInfo() != null)
                .collect(Collectors.toList());
        
        EconomicVO economic = new EconomicVO();
        economic.setTowns(validStats.stream()
                .map(stat -> stat.getTownInfo().getName())
                .collect(Collectors.toList()));
        economic.setGdp(validStats.stream()
                .map(stat -> stat.getGdp() != null ? stat.getGdp() : BigDecimal.ZERO)
                .collect(Collectors.toList()));
        economic.setIncome(validStats.stream()
                .map(stat -> stat.getIncome() != null ? stat.getIncome() : BigDecimal.ZERO)
                .collect(Collectors.toList()));
        
        return economic;
    }

    @Override
    public List<IndustryVO> getIndustryStructure(Integer year) {
        List<AnnualStatistics> statistics = annualStatisticsMapper.findByYear(year);
        
        // 如果数据为空，返回空的产业结构列表
        if (statistics == null || statistics.isEmpty()) {
            List<IndustryVO> emptyIndustries = new ArrayList<>();
            IndustryVO primary = new IndustryVO();
            primary.setName("第一产业");
            primary.setValue(BigDecimal.ZERO);
            
            IndustryVO secondary = new IndustryVO();
            secondary.setName("第二产业");
            secondary.setValue(BigDecimal.ZERO);
            
            IndustryVO tertiary = new IndustryVO();
            tertiary.setName("第三产业");
            tertiary.setValue(BigDecimal.ZERO);
            
            emptyIndustries.add(primary);
            emptyIndustries.add(secondary);
            emptyIndustries.add(tertiary);
            return emptyIndustries;
        }
        
        BigDecimal totalPrimary = statistics.stream()
                .map(stat -> stat.getPrimaryIndustry() != null ? stat.getPrimaryIndustry() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(new BigDecimal(Math.max(1, statistics.size())), 2, RoundingMode.HALF_UP);
        
        BigDecimal totalSecondary = statistics.stream()
                .map(stat -> stat.getSecondaryIndustry() != null ? stat.getSecondaryIndustry() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(new BigDecimal(Math.max(1, statistics.size())), 2, RoundingMode.HALF_UP);
        
        BigDecimal totalTertiary = statistics.stream()
                .map(stat -> stat.getTertiaryIndustry() != null ? stat.getTertiaryIndustry() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(new BigDecimal(Math.max(1, statistics.size())), 2, RoundingMode.HALF_UP);

        List<IndustryVO> industries = new ArrayList<>();
        
        IndustryVO primary = new IndustryVO();
        primary.setName("第一产业");
        primary.setValue(totalPrimary != null ? totalPrimary : BigDecimal.ZERO);
        industries.add(primary);

        IndustryVO secondary = new IndustryVO();
        secondary.setName("第二产业");
        secondary.setValue(totalSecondary != null ? totalSecondary : BigDecimal.ZERO);
        industries.add(secondary);

        IndustryVO tertiary = new IndustryVO();
        tertiary.setName("第三产业");
        tertiary.setValue(totalTertiary != null ? totalTertiary : BigDecimal.ZERO);
        industries.add(tertiary);

        return industries;
    }

    @Override
    public TrendVO getDevelopmentTrend(Integer startYear, Integer endYear) {
        TrendVO trend = new TrendVO();
        List<String> years = new ArrayList<>();
        List<BigDecimal> gdpList = new ArrayList<>();
        List<BigDecimal> populationList = new ArrayList<>();

        for (int year = startYear; year <= endYear; year++) {
            List<AnnualStatistics> statistics = annualStatisticsMapper.findByYear(year);
            
            years.add(String.valueOf(year));
            
            BigDecimal totalGdp = statistics.stream()
                    .map(AnnualStatistics::getGdp)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            gdpList.add(totalGdp);
            
            BigDecimal totalPopulation = new BigDecimal(statistics.stream()
                    .mapToInt(AnnualStatistics::getPopulation)
                    .sum())
                    .divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP); // 转换为万人
            populationList.add(totalPopulation);
        }

        trend.setYears(years);
        trend.setGdp(gdpList);
        trend.setPopulation(populationList);
        
        return trend;
    }

    @Override
    public TownDetailVO getTownDetail(String townId) {
        TownInfo townInfo = townInfoMapper.selectById(townId);
        if (townInfo == null) {
            return null;
        }

        TownDetailVO detail = new TownDetailVO();
        detail.setId(townInfo.getId());
        detail.setName(townInfo.getName());
        detail.setPopulation(townInfo.getPopulation());
        detail.setGdp(townInfo.getGdp());
        detail.setIncome(townInfo.getIncome());
        
        TownDetailVO.IndustryStructure industries = new TownDetailVO.IndustryStructure();
        industries.setPrimary(townInfo.getPrimaryIndustry());
        industries.setSecondary(townInfo.getSecondaryIndustry());
        industries.setTertiary(townInfo.getTertiaryIndustry());
        detail.setIndustries(industries);
        
        detail.setDescription(townInfo.getDescription());
        detail.setFeatures(townInfo.getFeatures());
        
        TownDetailVO.Contact contact = new TownDetailVO.Contact();
        contact.setAddress(townInfo.getAddress());
        contact.setPhone(townInfo.getPhone());
        contact.setEmail(townInfo.getEmail());
        detail.setContact(contact);
        
        return detail;
    }
} 