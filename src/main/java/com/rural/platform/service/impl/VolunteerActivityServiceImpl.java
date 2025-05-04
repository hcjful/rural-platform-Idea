package com.rural.platform.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rural.platform.entity.VolunteerActivity;
import com.rural.platform.entity.VolunteerSignup;
import com.rural.platform.mapper.VolunteerActivityMapper;
import com.rural.platform.mapper.VolunteerSignupMapper;
import com.rural.platform.service.VolunteerActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VolunteerActivityServiceImpl extends ServiceImpl<VolunteerActivityMapper, VolunteerActivity> implements VolunteerActivityService {
    private final VolunteerActivityMapper activityMapper;
    private final VolunteerSignupMapper signupMapper;

    @Override
    public org.springframework.data.domain.Page<VolunteerActivity> searchActivities(String status, String category, String search, Pageable pageable) {
        // 转换 Spring Data 的 Pageable 为 MyBatis Plus 的 Page
        Page<VolunteerActivity> page = new Page<>(pageable.getPageNumber() + 1, pageable.getPageSize());
        
        // 执行分页查询
        Page<VolunteerActivity> result = activityMapper.selectPage(page, new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<VolunteerActivity>()
                .eq(status != null && !status.isEmpty(), VolunteerActivity::getStatus, status)
                .eq(category != null && !category.isEmpty(), VolunteerActivity::getCategory, category)
                .and(search != null && !search.isEmpty(), wrapper -> wrapper
                        .like(VolunteerActivity::getTitle, search)
                        .or()
                        .like(VolunteerActivity::getDescription, search))
                .orderByDesc(VolunteerActivity::getCreatedAt));

        // 转换回 Spring Data 的 Page
        return new PageImpl<>(result.getRecords(), pageable, result.getTotal());
    }

    @Override
    public VolunteerActivity getActivityById(Long id) {
        return activityMapper.selectById(id);
    }

    @Override
    @Transactional
    public VolunteerActivity createActivity(VolunteerActivity activity) {
        activity.setCurrentVolunteers(0);
        activity.setStatus("recruiting");
        activityMapper.insert(activity);
        return activity;
    }

    @Override
    @Transactional
    public VolunteerSignup signup(Long activityId, VolunteerSignup signup) {
        VolunteerActivity activity = getActivityById(activityId);
        
        if (activity.getCurrentVolunteers() >= activity.getMaxVolunteers()) {
            throw new RuntimeException("报名人数已满");
        }
        
        if (!"recruiting".equals(activity.getStatus())) {
            throw new RuntimeException("活动不在报名阶段");
        }

        signup.setActivity(activity);
        signup.setStatus("pending");
        signupMapper.insert(signup);

        activity.setCurrentVolunteers(activity.getCurrentVolunteers() + 1);
        activityMapper.updateById(activity);

        return signup;
    }

    @Override
    @Transactional
    public void updateActivityStatus(Long activityId, String status) {
        VolunteerActivity activity = getActivityById(activityId);
        activity.setStatus(status);
        activityMapper.updateById(activity);
    }

    @Override
    @Transactional
    public void addActivityReview(Long activityId, String review, String reviewImages) {
        VolunteerActivity activity = getActivityById(activityId);
        activity.setReview(review);
        activity.setReviewImages(reviewImages);
        activity.setStatus("finished");
        activityMapper.updateById(activity);
    }
} 