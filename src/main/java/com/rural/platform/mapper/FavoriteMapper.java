package com.rural.platform.mapper;

import com.rural.platform.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface FavoriteMapper {
    
    /**
     * 根据用户ID查询收藏列表
     */
    List<Favorite> findByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID和商品ID查询收藏记录
     */
    Favorite findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    /**
     * 根据商品ID查询收藏列表
     */
    List<Favorite> findByProductId(@Param("productId") Long productId);

    /**
     * 统计商品的收藏数量
     */
    int countByProductId(@Param("productId") Long productId);

    /**
     * 添加收藏记录
     */
    int insert(Favorite favorite);

    /**
     * 根据ID删除收藏记录
     */
    int deleteById(@Param("id") Long id);

    /**
     * 根据用户ID和商品ID删除收藏记录
     */
    int deleteByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
} 