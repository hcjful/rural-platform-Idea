package com.rural.platform.mapper;

import com.rural.platform.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ProductMapper {
    
    Product findById(Long id);
    
    List<Product> findAll();
    
    List<Product> findByCategory(String category);
    
    List<Product> searchByKeyword(String keyword);
    
    int insert(Product product);
    
    int updateStock(@Param("id") Long id, @Param("stock") Integer stock);
    
    int deleteById(Long id);
} 