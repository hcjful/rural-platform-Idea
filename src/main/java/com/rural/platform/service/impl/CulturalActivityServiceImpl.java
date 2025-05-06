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
        System.out.println("接收到的DTO数据: " + activityDTO.getStartTime() + ", " + activityDTO.getEndTime());
        
        CulturalActivity activity = new CulturalActivity();
        
        // 先设置时间字段，避免被BeanUtils覆盖
        if (activityDTO.getStartTime() != null) {
            try {
                LocalDateTime startTime = LocalDateTime.parse(activityDTO.getStartTime(), formatter);
                activity.setStartTime(startTime);
                System.out.println("转换后的开始时间: " + startTime);
            } catch (Exception e) {
                throw new RuntimeException("开始时间格式错误: " + activityDTO.getStartTime(), e);
            }
        }
        
        if (activityDTO.getEndTime() != null) {
            try {
                LocalDateTime endTime = LocalDateTime.parse(activityDTO.getEndTime(), formatter);
                activity.setEndTime(endTime);
                System.out.println("转换后的结束时间: " + endTime);
            } catch (Exception e) {
                throw new RuntimeException("结束时间格式错误: " + activityDTO.getEndTime(), e);
            }
        }
        
        // 复制其他属性
        BeanUtils.copyProperties(activityDTO, activity, "startTime", "endTime");
        
        // 设置必填字段的默认值
        activity.setStatus(1);
        activity.setViewCount(0);
        activity.setCreateTime(LocalDateTime.now());
        activity.setUpdateTime(LocalDateTime.now());
        activity.setCurrentParticipants(0);
        
        // 设置默认值
        if (activity.getOrganizer() == null || activity.getOrganizer().trim().isEmpty()) {
            activity.setOrganizer("管理员");
        }
        if (activity.getAuthor() == null || activity.getAuthor().trim().isEmpty()) {
            activity.setAuthor("管理员");
        }
        if (activity.getMaxParticipants() == null) {
            activity.setMaxParticipants(100);
        }
        
        System.out.println("即将保存的实体数据:");
        System.out.println("- 开始时间: " + activity.getStartTime());
        System.out.println("- 结束时间: " + activity.getEndTime());
        System.out.println("- 标题: " + activity.getTitle());
        System.out.println("- 地点: " + activity.getLocation());
        System.out.println("- 组织者: " + activity.getOrganizer());
        
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