package com.rural.platform.service;

import com.rural.platform.dto.*;
import com.rural.platform.response.Result;
import com.rural.platform.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

public interface UserProfileService {
    Result<UserVO> getUserInfo();
    Result<?> updateUserInfo(UserInfoDTO userInfo);
    Result<String> updateAvatar(MultipartFile file);
    Result<?> changePassword(PasswordDTO passwordDTO);
    Result<?> updatePhone(PhoneDTO phoneDTO);
    Result<?> updateEmail(EmailDTO emailDTO);
    Result<?> getLoginDevices();
    Result<?> logoutDevice(Long deviceId);
    Result<?> getNotificationSettings();
    Result<?> updateNotificationSettings(NotificationSettingsDTO settingsDTO);
    Result<?> getPublishedActivities(Integer page, Integer pageSize);
    Result<?> getParticipatedActivities(Integer page, Integer pageSize);
}
