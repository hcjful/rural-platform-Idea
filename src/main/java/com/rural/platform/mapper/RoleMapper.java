package com.rural.platform.mapper;

import com.rural.platform.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface RoleMapper {
    
    int insert(Role role);
    
    int update(Role role);
    
    int delete(Long id);
    
    Role findById(Long id);
    
    Role findByCode(String code);
    
    List<Role> findAll();
    
    List<Role> findByUserId(Long userId);
} 