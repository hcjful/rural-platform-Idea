package com.rural.platform.mapper;

import com.rural.platform.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface OrderDetailMapper {
    
    List<OrderDetail> findByOrderId(Long orderId);
    
    int insert(OrderDetail orderDetail);
    
    int deleteByOrderId(Long orderId);
} 