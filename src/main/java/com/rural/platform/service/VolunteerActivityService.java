package com.rural.platform.service;

import com.rural.platform.entity.VolunteerActivity;
import com.rural.platform.entity.VolunteerSignup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VolunteerActivityService {
    // 搜索活动
    Page<VolunteerActivity> searchActivities(String status, String category, String search, Pageable pageable);
    
    // 获取活动详情
    VolunteerActivity getActivityById(Long id);
    
    // 创建活动
    VolunteerActivity createActivity(VolunteerActivity activity);
    
    // 报名活动
    VolunteerSignup signup(Long activityId, VolunteerSignup signup);
    
    // 更新活动状态
    void updateActivityStatus(Long activityId, String status);
    
    // 添加活动评价
    void addActivityReview(Long activityId, String review, String reviewImages);
} 
