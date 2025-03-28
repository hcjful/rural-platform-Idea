package com.rural.platform.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.rural.platform.utils.CacheUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @GetMapping("/captchaImage")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 创建线条干扰的验证码
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(120, 40, 4, 5);

        // 设置字体
        lineCaptcha.setFont(new Font("Arial", Font.PLAIN, 20));

        // 获取验证码中的文字内容
        String code = lineCaptcha.getCode();

        // 使用唯一的key存储验证码
        CacheUtil.captchaMap.put("127.0.0.1", code);

        // 将验证码文本保存到本地文件
        System.out.println(code);

        // 设置响应类型
        response.setContentType(MediaType.IMAGE_PNG_VALUE);

        // 将验证码图片写入响应
        lineCaptcha.write(response.getOutputStream());
    }
}