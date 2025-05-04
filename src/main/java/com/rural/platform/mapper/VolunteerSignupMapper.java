package com.rural.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rural.platform.entity.VolunteerSignup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VolunteerSignupMapper extends BaseMapper<VolunteerSignup> {
    
    // 根据活动ID查询报名信息
    List<VolunteerSignup> findByActivityId(@Param("activityId") Long activityId);
    
    // 根据活动ID和状态统计报名人数
    int countByActivityIdAndStatus(@Param("activityId") Long activityId, @Param("status") String status);
} 