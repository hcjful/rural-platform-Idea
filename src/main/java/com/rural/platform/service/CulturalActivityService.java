package com.rural.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rural.platform.dto.CulturalActivityDTO;
import com.rural.platform.entity.CulturalActivity;

public interface CulturalActivityService {
    IPage<CulturalActivityDTO> getActivityPage(Integer page, Integer pageSize, String category, String searchKey);
    
    CulturalActivityDTO getActivityById(Long id);
    
    Long createActivity(CulturalActivityDTO activityDTO);
    
    void updateActivity(Long id, CulturalActivityDTO activityDTO);
    
    void deleteActivity(Long id);
    
    void incrementViewCount(Long id);
} 