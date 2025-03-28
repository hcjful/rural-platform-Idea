package com.rural.platform.mapper;

import com.rural.platform.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserRoleMapper {
    
    int insert(UserRole userRole);
    
    int delete(Long id);
    
    int deleteByUserAndRole(@Param("userId") Long userId, @Param("roleId") Long roleId);
    
    int deleteByUserId(Long userId);
    
    List<UserRole> findByUserId(Long userId);
    
    List<UserRole> findByRoleId(Long roleId);
    
    UserRole findByUserAndRole(@Param("userId") Long userId, @Param("roleId") Long roleId);
} 