package com.rural.platform.mapper;

import com.rural.platform.entity.Review;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ReviewMapper {
    
    int insert(Review review);
    
    List<Review> findByProductId(Long productId);
    
    List<Review> findByUserId(Long userId);
    
    Double getAverageRating(Long productId);
} 