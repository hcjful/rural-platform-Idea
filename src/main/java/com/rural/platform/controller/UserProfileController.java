package com.rural.platform.controller;

import com.rural.platform.dto.UserInfoDTO;
import com.rural.platform.dto.PasswordDTO;
import com.rural.platform.dto.PhoneDTO;
import com.rural.platform.dto.EmailDTO;
import com.rural.platform.dto.NotificationSettingsDTO;
import com.rural.platform.response.Result;
import com.rural.platform.service.UserProfileService;
import com.rural.platform.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import com.rural.platform.utils.SecurityUtils;
import com.rural.platform.entity.User;
import com.rural.platform.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/user")
public class UserProfileController {
    private static final Logger log = LoggerFactory.getLogger(UserProfileController.class);

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/info")
    public Result<UserVO> getUserInfo() {
        Long userId = SecurityUtils.getUserId();
        log.info("Getting user info for userId: {}", userId);
        
        if (userId == null) {
            log.warn("Failed to get userId from SecurityUtils");
            return Result.error("用户未登录或会话已过期");
        }
        
        User user = userMapper.selectById(userId);
        if (user == null) {
            log.warn("User not found for userId: {}", userId);
            return Result.error("用户不存在");
        }
        
        if ("1".equals(user.getIsDelete())) {
            log.warn("User has been deleted: {}", userId);
            return Result.error("用户已被删除");
        }
        
        if (!"1".equals(user.getUserState())) {
            log.warn("User state is invalid: {}", user.getUserState());
            return Result.error("用户状态异常");
        }
        
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        log.info("Successfully retrieved user info for userId: {}", userId);
        return Result.success(userVO);
    }

    @PutMapping("/info")
    public Result<?> updateUserInfo(@Valid @RequestBody UserInfoDTO userInfo) {
        Long userId = SecurityUtils.getUserId();
        log.info("Updating user info for userId: {}", userId);
        
        if (userId == null) {
            log.warn("Failed to get userId from SecurityUtils");
            return Result.error("用户未登录或会话已过期");
        }
        
        return userProfileService.updateUserInfo(userInfo);
    }

    @PostMapping("/avatar")
    public Result<String> updateAvatar(@RequestParam("file") MultipartFile file) {
        Long userId = SecurityUtils.getUserId();
        log.info("Updating avatar for userId: {}", userId);
        
        if (userId == null) {
            log.warn("Failed to get userId from SecurityUtils");
            return Result.error("用户未登录或会话已过期");
        }
        
        return userProfileService.updateAvatar(file);
    }

    @PutMapping("/password")
    public Result<?> changePassword(@Valid @RequestBody PasswordDTO passwordDTO) {
        Long userId = SecurityUtils.getUserId();
        log.info("Changing password for userId: {}", userId);
        
        if (userId == null) {
            log.warn("Failed to get userId from SecurityUtils");
            return Result.error("用户未登录或会话已过期");
        }
        
        return userProfileService.changePassword(passwordDTO);
    }

    @PutMapping("/phone")
    public Result<?> updatePhone(@Valid @RequestBody PhoneDTO phoneDTO) {
        Long userId = SecurityUtils.getUserId();
        log.info("Updating phone for userId: {}", userId);
        
        if (userId == null) {
            log.warn("Failed to get userId from SecurityUtils");
            return Result.error("用户未登录或会话已过期");
        }
        
        return userProfileService.updatePhone(phoneDTO);
    }

    @PutMapping("/email")
    public Result<?> updateEmail(@Valid @RequestBody EmailDTO emailDTO) {
        Long userId = SecurityUtils.getUserId();
        log.info("Updating email for userId: {}", userId);
        
        if (userId == null) {
            log.warn("Failed to get userId from SecurityUtils");
            return Result.error("用户未登录或会话已过期");
        }
        
        return userProfileService.updateEmail(emailDTO);
    }

    @GetMapping("/devices")
    public Result<?> getLoginDevices() {
        Long userId = SecurityUtils.getUserId();
        log.info("Getting login devices for userId: {}", userId);
        
        if (userId == null) {
            log.warn("Failed to get userId from SecurityUtils");
            return Result.error("用户未登录或会话已过期");
        }
        
        return userProfileService.getLoginDevices();
    }

    @DeleteMapping("/devices/{deviceId}")
    public Result<?> logoutDevice(@PathVariable Long deviceId) {
        Long userId = SecurityUtils.getUserId();
        log.info("Logging out device {} for userId: {}", deviceId, userId);
        
        if (userId == null) {
            log.warn("Failed to get userId from SecurityUtils");
            return Result.error("用户未登录或会话已过期");
        }
        
        return userProfileService.logoutDevice(deviceId);
    }

    @GetMapping("/notification/settings")
    public Result<?> getNotificationSettings() {
        Long userId = SecurityUtils.getUserId();
        log.info("Getting notification settings for userId: {}", userId);
        
        if (userId == null) {
            log.warn("Failed to get userId from SecurityUtils");
            return Result.error("用户未登录或会话已过期");
        }
        
        return userProfileService.getNotificationSettings();
    }

    @PutMapping("/notification/settings")
    public Result<?> updateNotificationSettings(@Valid @RequestBody NotificationSettingsDTO settings) {
        Long userId = SecurityUtils.getUserId();
        log.info("Updating notification settings for userId: {}", userId);
        
        if (userId == null) {
            log.warn("Failed to get userId from SecurityUtils");
            return Result.error("用户未登录或会话已过期");
        }
        
        return userProfileService.updateNotificationSettings(settings);
    }

    @GetMapping("/activities/published")
    public Result<?> getPublishedActivities(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = SecurityUtils.getUserId();
        log.info("Getting published activities for userId: {}, page: {}, pageSize: {}", 
            userId, page, pageSize);
        
        if (userId == null) {
            log.warn("Failed to get userId from SecurityUtils");
            return Result.error("用户未登录或会话已过期");
        }
        
        return userProfileService.getPublishedActivities(page, pageSize);
    }

    @GetMapping("/activities/participated")
    public Result<?> getParticipatedActivities(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = SecurityUtils.getUserId();
        log.info("Getting participated activities for userId: {}, page: {}, pageSize: {}", 
            userId, page, pageSize);
        
        if (userId == null) {
            log.warn("Failed to get userId from SecurityUtils");
            return Result.error("用户未登录或会话已过期");
        }
        
        return userProfileService.getParticipatedActivities(page, pageSize);
    }
}
