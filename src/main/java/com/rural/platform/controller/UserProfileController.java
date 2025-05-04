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

@RestController
@RequestMapping("/user")
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/info")
    public Result<UserVO> getUserInfo() {
        return userProfileService.getUserInfo();
    }

    @PutMapping("/info")
    public Result<?> updateUserInfo(@Valid @RequestBody UserInfoDTO userInfo) {
        return userProfileService.updateUserInfo(userInfo);
    }

    @PostMapping("/avatar")
    public Result<String> updateAvatar(@RequestParam("file") MultipartFile file) {
        return userProfileService.updateAvatar(file);
    }

    @PutMapping("/password")
    public Result<?> changePassword(@Valid @RequestBody PasswordDTO passwordDTO) {
        return userProfileService.changePassword(passwordDTO);
    }

    @PutMapping("/phone")
    public Result<?> updatePhone(@Valid @RequestBody PhoneDTO phoneDTO) {
        return userProfileService.updatePhone(phoneDTO);
    }

    @PutMapping("/email")
    public Result<?> updateEmail(@Valid @RequestBody EmailDTO emailDTO) {
        return userProfileService.updateEmail(emailDTO);
    }

    @GetMapping("/devices")
    public Result<?> getLoginDevices() {
        return userProfileService.getLoginDevices();
    }

    @DeleteMapping("/devices/{deviceId}")
    public Result<?> logoutDevice(@PathVariable Long deviceId) {
        return userProfileService.logoutDevice(deviceId);
    }

    @GetMapping("/notification/settings")
    public Result<?> getNotificationSettings() {
        return userProfileService.getNotificationSettings();
    }

    @PutMapping("/notification/settings")
    public Result<?> updateNotificationSettings(@Valid @RequestBody NotificationSettingsDTO settings) {
        return userProfileService.updateNotificationSettings(settings);
    }

    @GetMapping("/activities/published")
    public Result<?> getPublishedActivities(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        return userProfileService.getPublishedActivities(page, pageSize);
    }

    @GetMapping("/activities/participated")
    public Result<?> getParticipatedActivities(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return userProfileService.getParticipatedActivities(page, pageSize);
    }
}
