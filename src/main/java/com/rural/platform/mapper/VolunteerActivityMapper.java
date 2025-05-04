package com.rural.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rural.platform.entity.VolunteerActivity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VolunteerActivityMapper extends BaseMapper<VolunteerActivity> {
    
    /**
     * 分页搜索活动
     */
    IPage<VolunteerActivity> searchActivities(
            IPage<VolunteerActivity> page,
            @Param("status") String status,
            @Param("category") String category,
            @Param("search") String search
    );
} 