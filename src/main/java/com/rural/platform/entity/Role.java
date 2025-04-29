package com.rural.platform.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Role {
    private Long id;
    private String roleName;  // 角色名称
    private String roleCode;  // 角色编码
    private String description;  // 角色描述
    private LocalDateTime createdAt;
}