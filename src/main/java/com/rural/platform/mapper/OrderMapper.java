package com.rural.platform.mapper;

import com.rural.platform.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface OrderMapper {
    
    Order findById(Long id);
    
    List<Order> findByUserId(Long userId);
    
    int insert(Order order);
    
    int updateStatus(@Param("id") Long id, @Param("status") String status);
    
    int deleteById(Long id);
    
    List<Order> search(@Param("userId") Long userId, 
                      @Param("status") String status, 
                      @Param("startTime") String startTime,
                      @Param("endTime") String endTime);
} 