package com.rural.platform.mapper;

import com.rural.platform.entity.LogisticsTrack;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface LogisticsTrackMapper {
    
    List<LogisticsTrack> findByLogisticsId(Long logisticsId);
    
    int insert(LogisticsTrack track);
} 