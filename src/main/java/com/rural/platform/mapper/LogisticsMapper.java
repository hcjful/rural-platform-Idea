package com.rural.platform.mapper;

import com.rural.platform.entity.Logistics;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogisticsMapper {
    
    Logistics findById(Long id);
    
    Logistics findByOrderId(Long orderId);
    
    int insert(Logistics logistics);
    
    int updateStatus(Logistics logistics);
} 