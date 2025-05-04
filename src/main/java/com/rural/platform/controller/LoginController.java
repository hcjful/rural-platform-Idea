package com.rural.platform.controller;
import com.rural.platform.response.Response;
import com.rural.platform.service.UserService;
import com.rural.platform.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public Response<String> login(@RequestBody UserInfoVo userInfoVo, HttpServletRequest request){
        return  userService.login(userInfoVo);
    }
}
