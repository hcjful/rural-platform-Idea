package com.rural.platform.service.impl;

import com.rural.platform.service.UserProfileService;
import com.rural.platform.response.Result;
import com.rural.platform.vo.UserVO;
import com.rural.platform.dto.*;
import com.rural.platform.entity.*;
import com.rural.platform.mapper.*;
import com.rural.platform.utils.SecurityUtils;
import com.rural.platform.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.alibaba.fastjson.JSON;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import org.springframework.beans.BeanUtils;
import com.rural.platform.service.SmsService;

@Service
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDeviceMapper userDeviceMapper;
    @Autowired
    private UserNotificationSettingsMapper notificationSettingsMapper;
    @Autowired
    private VolunteerActivityMapper volunteerActivityMapper;
    @Autowired
    private CulturalActivityMapper culturalActivityMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SmsService smsService;

    @Override
    public Result<UserVO> getUserInfo() {
        Long userId = SecurityUtils.getUserId();
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return Result.success(userVO);
    }

    @Override
    @Transactional
    public Result<?> updateUserInfo(UserInfoDTO userInfo) {
        Long userId = SecurityUtils.getUserId();
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }

        // 更新用户信息
        user.setUserName(userInfo.getUserName());
        user.setRealName(userInfo.getRealName());
        user.setGender(userInfo.getGender());
        user.setPhone(userInfo.getPhone());
        user.setEmail(userInfo.getEmail());
        user.setLocation(userInfo.getLocation());
        user.setBio(userInfo.getBio());
        
        int rows = userMapper.updateById(user);
        return rows > 0 ? Result.success() : Result.error("更新失败");
    }

    @Override
    public Result<String> updateAvatar(MultipartFile file) {
        if (file == null) {
            return Result.error("请选择文件");
        }
        try {
            String fileName = FileUploadUtils.upload(file, "avatar");
            Long userId = SecurityUtils.getUserId();
            userMapper.updateAvatar(userId, fileName);
            return Result.success(fileName);
        } catch (Exception e) {
            log.error("上传头像失败", e);
            return Result.error("上传头像失败");
        }
    }

    @Override
    public Result<?> changePassword(PasswordDTO passwordDTO) {
        Long userId = SecurityUtils.getUserId();
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }

        // 验证旧密码
        if (!passwordEncoder.matches(passwordDTO.getOldPassword(), user.getUserPwd())) {
            return Result.error("原密码错误");
        }

        // 验证两次密码是否一致
        if (!passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())) {
            return Result.error("两次输入的密码不一致");
        }

        // 更新密码
        user.setUserPwd(passwordEncoder.encode(passwordDTO.getNewPassword()));
        int rows = userMapper.updateById(user);
        return rows > 0 ? Result.success() : Result.error("修改密码失败");
    }

    @Override
    public Result<?> updatePhone(PhoneDTO phoneDTO) {
        Long userId = SecurityUtils.getUserId();
        // 验证短信验证码
        if (!smsService.verifyCode(phoneDTO.getPhone(), phoneDTO.getCode())) {
            return Result.error("验证码错误或已过期");
        }
        int rows = userMapper.updatePhone(userId, phoneDTO.getPhone());
        return rows > 0 ? Result.success() : Result.error("更新手机号失败");
    }

    @Override
    public Result<?> updateEmail(EmailDTO emailDTO) {
        Long userId = SecurityUtils.getUserId();
        int rows = userMapper.updateEmail(userId, emailDTO.getEmail());
        return rows > 0 ? Result.success() : Result.error("更新邮箱失败");
    }

    @Override
    public Result<List<UserDevice>> getLoginDevices() {
        Long userId = SecurityUtils.getUserId();
        List<UserDevice> devices = userDeviceMapper.selectByUserId(userId);
        return Result.success(devices);
    }

    @Override
    public Result<?> logoutDevice(Long deviceId) {
        Long userId = SecurityUtils.getUserId();
        UserDevice device = userDeviceMapper.selectById(deviceId);
        if (device == null || !device.getUserId().equals(userId)) {
            return Result.error("设备不存在或无权操作");
        }
        int rows = userDeviceMapper.deleteById(deviceId);
        return rows > 0 ? Result.success() : Result.error("退出设备失败");
    }

    @Override
    public Result<NotificationSettingsDTO> getNotificationSettings() {
        Long userId = SecurityUtils.getUserId();
        UserNotificationSettings settings = notificationSettingsMapper.selectByUserId(userId);
        if (settings == null) {
            // 如果没有设置，返回默认设置
            settings = new UserNotificationSettings();
            settings.setUserId(userId);
            settings.setSystemNotify(true);
            settings.setActivityNotify(true);
            settings.setCommentNotify(true);
            settings.setMessageNotify(true);
            settings.setNotifyMethods("[\"site\",\"email\"]");
            notificationSettingsMapper.insert(settings);
        }
        
        NotificationSettingsDTO dto = new NotificationSettingsDTO();
        dto.setSystemNotify(settings.getSystemNotify());
        dto.setActivityNotify(settings.getActivityNotify());
        dto.setCommentNotify(settings.getCommentNotify());
        dto.setMessageNotify(settings.getMessageNotify());
        dto.setNotifyMethods(JSON.parseArray(settings.getNotifyMethods(), String.class));
        
        return Result.success(dto);
    }

    @Override
    public Result<?> updateNotificationSettings(NotificationSettingsDTO settingsDTO) {
        Long userId = SecurityUtils.getUserId();
        UserNotificationSettings settings = new UserNotificationSettings();
        settings.setUserId(userId);
        settings.setSystemNotify(settingsDTO.getSystemNotify());
        settings.setActivityNotify(settingsDTO.getActivityNotify());
        settings.setCommentNotify(settingsDTO.getCommentNotify());
        settings.setMessageNotify(settingsDTO.getMessageNotify());
        settings.setNotifyMethods(JSON.toJSONString(settingsDTO.getNotifyMethods()));
        
        int rows = notificationSettingsMapper.updateByUserId(settings);
        return rows > 0 ? Result.success() : Result.error("更新通知设置失败");
    }

    @Override
    public Result<Map<String, Object>> getPublishedActivities(Integer page, Integer pageSize) {
        Long userId = SecurityUtils.getUserId();
        // 计算偏移量
        int offset = (page - 1) * pageSize;
        
        // 获取活动列表和总数
        List<Map<String, Object>> activities = new ArrayList<>();
        activities.addAll(volunteerActivityMapper.selectPublishedByUserId(userId, offset, pageSize));
        activities.addAll(culturalActivityMapper.selectPublishedByUserId(userId, offset, pageSize));
        
        int total = volunteerActivityMapper.countPublishedByUserId(userId) +
                   culturalActivityMapper.countPublishedByUserId(userId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("list", activities);
        result.put("total", total);
        
        return Result.success(result);
    }

    @Override
    public Result<Map<String, Object>> getParticipatedActivities(Integer page, Integer pageSize) {
        Long userId = SecurityUtils.getUserId();
        // 计算偏移量
        int offset = (page - 1) * pageSize;
        
        // 获取活动列表和总数
        List<Map<String, Object>> activities = new ArrayList<>();
        activities.addAll(volunteerActivityMapper.selectParticipatedByUserId(userId, offset, pageSize));
        activities.addAll(culturalActivityMapper.selectParticipatedByUserId(userId, offset, pageSize));
        
        int total = volunteerActivityMapper.countParticipatedByUserId(userId) +
                   culturalActivityMapper.countParticipatedByUserId(userId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("list", activities);
        result.put("total", total);
        
        return Result.success(result);
    }
}
