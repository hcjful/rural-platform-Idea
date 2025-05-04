package com.rural.platform.mapper;

import com.rural.platform.entity.UserNotificationSettings;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserNotificationSettingsMapper {
    UserNotificationSettings selectByUserId(Long userId);
    int updateByUserId(UserNotificationSettings settings);
    int insert(UserNotificationSettings settings);
}
