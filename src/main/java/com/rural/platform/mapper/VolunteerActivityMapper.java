package com.rural.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rural.platform.entity.VolunteerActivity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

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

    /**
     * 查询用户发布的志愿活动
     */
    List<Map<String, Object>> selectPublishedByUserId(@Param("userId") Long userId, 
                                                     @Param("offset") Integer offset, 
                                                     @Param("pageSize") Integer pageSize);
    
    /**
     * 统计用户发布的志愿活动数量
     */
    int countPublishedByUserId(@Param("userId") Long userId);
    
    /**
     * 查询用户参与的志愿活动
     */
    List<Map<String, Object>> selectParticipatedByUserId(@Param("userId") Long userId, 
                                                        @Param("offset") Integer offset, 
                                                        @Param("pageSize") Integer pageSize);
    
    /**
     * 统计用户参与的志愿活动数量
     */
    int countParticipatedByUserId(@Param("userId") Long userId);
} 