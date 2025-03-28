package com.rural.platform.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String userCode;
    private String userName;
    private String userPwd;
    private String email;
    private String phone;
    // 预留验证码字段
    private String verificationCode;

}