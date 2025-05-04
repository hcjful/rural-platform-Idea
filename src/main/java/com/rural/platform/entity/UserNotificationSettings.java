package com.rural.platform.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserNotificationSettings {
    private Long id;
    private Long userId;
    private Boolean systemNotify;
    private Boolean activityNotify;
    private Boolean commentNotify;
    private Boolean messageNotify;
    private String notifyMethods;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
