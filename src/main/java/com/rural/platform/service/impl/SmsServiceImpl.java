package com.rural.platform.service.impl;

import com.rural.platform.service.SmsService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;
import java.util.Random;

@Service
public class SmsServiceImpl implements SmsService {
    private final StringRedisTemplate redisTemplate;
    private static final String SMS_CODE_PREFIX = "sms:code:";
    private static final long CODE_EXPIRE_TIME = 5; // 验证码有效期5分钟

    public SmsServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean verifyCode(String phone, String code) {
        String key = SMS_CODE_PREFIX + phone;
        String savedCode = redisTemplate.opsForValue().get(key);
        if (savedCode != null && savedCode.equals(code)) {
            redisTemplate.delete(key);
            return true;
        }
        return false;
    }

    @Override
    public void sendCode(String phone) {
        String code = generateCode();
        redisTemplate.opsForValue().set(
            SMS_CODE_PREFIX + phone, 
            code, 
            CODE_EXPIRE_TIME, 
            TimeUnit.MINUTES
        );
        // TODO: 调用实际的短信发送服务
        System.out.println("发送验证码到" + phone + ": " + code);
    }

    private String generateCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}