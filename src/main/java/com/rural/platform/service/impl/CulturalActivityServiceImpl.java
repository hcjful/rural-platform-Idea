package com.rural.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rural.platform.dto.CulturalActivityDTO;
import com.rural.platform.entity.CulturalActivity;
import com.rural.platform.mapper.CulturalActivityMapper;
import com.rural.platform.service.CulturalActivityService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
public class CulturalActivityServiceImpl implements CulturalActivityService {
    
    @Resource
    private CulturalActivityMapper culturalActivityMapper;
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public IPage<CulturalActivityDTO> getActivityPage(Integer page, Integer pageSize, String category, String searchKey) {
        page = Math.max(1, page);
        Page<CulturalActivity> pageParam = new Page<>(page, pageSize);
        IPage<CulturalActivity> activityPage = culturalActivityMapper.selectPage(pageParam, 
            new QueryWrapper<CulturalActivity>()
                .eq("status", 1)
                .eq(category != null && !category.isEmpty(), "category", category)
                .and(searchKey != null && !searchKey.isEmpty(),
                    wrapper -> wrapper.like("title", searchKey)
                        .or()
                        .like("content", searchKey)
                        .or()
                        .like("author", searchKey))
                .orderByDesc("create_time"));

        return activityPage.convert(activity -> {
            CulturalActivityDTO dto = new CulturalActivityDTO();
            BeanUtils.copyProperties(activity, dto);
            dto.setCreateTime(activity.getCreateTime().format(formatter));
            return dto;
        });
    }

    @Override
    public CulturalActivityDTO getActivityById(Long id) {
        CulturalActivity activity = culturalActivityMapper.selectById(id);
        if (activity == null) {
            return null;
        }
        
        CulturalActivityDTO dto = new CulturalActivityDTO();
        BeanUtils.copyProperties(activity, dto);
        dto.setCreateTime(activity.getCreateTime().format(formatter));
        return dto;
    }

    @Override
    @Transactional
    public Long createActivity(CulturalActivityDTO activityDTO) {
        CulturalActivity activity = new CulturalActivity();
        BeanUtils.copyProperties(activityDTO, activity);
        activity.setStatus(1);
        activity.setViewCount(0);
        activity.setCreateTime(LocalDateTime.now());
        activity.setUpdateTime(LocalDateTime.now());
        
        // 如果未提供作者，使用默认值
        if (activity.getAuthor() == null || activity.getAuthor().trim().isEmpty()) {
            activity.setAuthor("管理员");
        }
        
        culturalActivityMapper.insert(activity);
        return activity.getId();
    }

    @Override
    @Transactional
    public void updateActivity(Long id, CulturalActivityDTO activityDTO) {
        CulturalActivity activity = new CulturalActivity();
        BeanUtils.copyProperties(activityDTO, activity);
        activity.setId(id);
        culturalActivityMapper.updateById(activity);
    }

    @Override
    @Transactional
    public void deleteActivity(Long id) {
        CulturalActivity activity = new CulturalActivity();
        activity.setId(id);
        activity.setStatus(0);
        culturalActivityMapper.updateById(activity);
    }

    @Override
    @Transactional
    public void incrementViewCount(Long id) {
        culturalActivityMapper.incrementViewCount(id);
    }
} 