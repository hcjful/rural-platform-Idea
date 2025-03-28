package com.baidu.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.baidu.utils.CacheUtil;
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
        lineCaptcha.setFont(new Font("Arial", Font.PLAIN, 20));  // 这里设置字体大小为20

        // 获取验证码中的文字内容
        String code = lineCaptcha.getCode();


        //获取到客户端的ip地址作为key 把验证码保存到map里面
        CacheUtil.captchaMap.put("127.0.0.1", code);

        // 将验证码文本保存到本地文件
        System.out.println(code);

        // 设置响应类型
        response.setContentType(MediaType.IMAGE_PNG_VALUE);

        // 将验证码图片写入响应
        lineCaptcha.write(response.getOutputStream());
    }
}
