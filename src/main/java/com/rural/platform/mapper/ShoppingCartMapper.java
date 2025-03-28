package com.rural.platform.mapper;

import com.rural.platform.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    
    List<ShoppingCart> findByUserId(Long userId);
    
    ShoppingCart findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
    
    int insert(ShoppingCart cart);
    
    int updateQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);
    
    int deleteById(Long id);
    
    int deleteByUserId(Long userId);
} 