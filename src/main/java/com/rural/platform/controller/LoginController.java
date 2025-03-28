package com.rural.platform.controller;
import com.rural.platform.response.Response;
import com.rural.platform.service.UserService;
import com.rural.platform.vo.UserInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public Response<String> login(@RequestBody UserInfoVo userInfoVo, HttpServletRequest request){
        return  userService.login(userInfoVo);
    }
}
