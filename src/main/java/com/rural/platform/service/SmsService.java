package com.rural.platform.service;

public interface SmsService {
    boolean verifyCode(String phone, String code);
    void sendCode(String phone);
}
