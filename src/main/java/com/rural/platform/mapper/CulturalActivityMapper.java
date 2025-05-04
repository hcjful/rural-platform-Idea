package com.rural.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rural.platform.entity.CulturalActivity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface CulturalActivityMapper extends BaseMapper<CulturalActivity> {
    /**
     * 查询用户发布的文化活动
     */
    List<Map<String, Object>> selectPublishedByUserId(@Param("userId") Long userId, 
                                                     @Param("offset") Integer offset, 
                                                     @Param("pageSize") Integer pageSize);
    
    /**
     * 统计用户发布的文化活动数量
     */
    int countPublishedByUserId(@Param("userId") Long userId);
    
    /**
     * 查询用户参与的文化活动
     */
    List<Map<String, Object>> selectParticipatedByUserId(@Param("userId") Long userId, 
                                                        @Param("offset") Integer offset, 
                                                        @Param("pageSize") Integer pageSize);
    
    /**
     * 统计用户参与的文化活动数量
     */
    int countParticipatedByUserId(@Param("userId") Long userId);

    /**
     * 增加浏览次数
     *
     * @param id 活动ID
     */
    void incrementViewCount(@Param("id") Long id);
} 