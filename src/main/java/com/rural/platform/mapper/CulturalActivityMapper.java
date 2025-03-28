package com.rural.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rural.platform.entity.CulturalActivity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CulturalActivityMapper extends BaseMapper<CulturalActivity> {
    /**
     * 增加浏览次数
     *
     * @param id 活动ID
     */
    void incrementViewCount(@Param("id") Long id);
} 