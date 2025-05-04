package com.rural.platform.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDevice {
    private Long id;
    private Long userId;
    private String deviceName;
    private String deviceType;
    private LocalDateTime lastLoginTime;
    private String lastLoginIp;
    private Boolean isCurrent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
