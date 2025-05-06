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
import java.time.LocalDateTime;

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

        // 处理结果，确保数值正确
        result.getRecords().forEach(activity -> {
            // 如果最大人数为空或者为0，设置默认值
            if (activity.getMaxVolunteers() == null || activity.getMaxVolunteers() <= 0) {
                activity.setMaxVolunteers(20);
            }
            // 如果当前人数为空，设置为0
            if (activity.getCurrentVolunteers() == null) {
                activity.setCurrentVolunteers(0);
            }
            // 更新到数据库
            activityMapper.updateById(activity);
        });

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
        // 设置默认值
        if (activity.getMaxVolunteers() == null || activity.getMaxVolunteers() <= 0) {
            activity.setMaxVolunteers(20); // 设置默认最大志愿者数量为20
        }
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
        
        // 允许 pending 和 recruiting 状态报名
        if (!"recruiting".equals(activity.getStatus()) && !"pending".equals(activity.getStatus())) {
            throw new RuntimeException("活动不在报名阶段");
        }

        // 设置活动ID而不是整个活动对象
        signup.setActivityId(activityId);
        signup.setStatus("pending");
        signup.setSignupTime(LocalDateTime.now());
        // TODO: 从当前登录用户中获取用户ID
        signup.setUserId(1L); // 这里需要替换为实际的用户ID
        signupMapper.insert(signup);

        // 更新活动的当前报名人数
        activity.setCurrentVolunteers(activity.getCurrentVolunteers() + 1);
        // 如果是 pending 状态，更新为 recruiting
        if ("pending".equals(activity.getStatus())) {
            activity.setStatus("recruiting");
        }
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