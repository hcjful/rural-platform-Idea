package com.rural.platform.mapper;

import com.rural.platform.entity.Payment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {
    
    Payment findByOrderId(Long orderId);
    
    Payment findByPaymentNo(String paymentNo);
    
    int insert(Payment payment);
    
    int updateStatus(Payment payment);
} 