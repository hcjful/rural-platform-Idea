package com.baidu.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baidu.entity.UserInfo;
import com.baidu.mapper.UserInfoMapper;
import com.baidu.response.Response;
import com.baidu.service.UserInfoService;
import com.baidu.utils.CacheUtil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.baidu.service.UserInfoService;
import vo.UserInfoVo;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public List<UserInfo> findAll() {
        return userInfoMapper.selectAll();
    }
    @Override
    public Response<String> login(UserInfoVo userInfoVo) {
        //判断验证码是否正确
        String code = CacheUtil.captchaMap.get("127.0.0.1");
        if (!code.equalsIgnoreCase(userInfoVo.getVerificationCode())) {
            return Response.fail("501","验证码错误");}
            //判断账号是否存在
            UserInfo userInfo = userInfoMapper.selectUserByCode(userInfoVo.getUserCode());
            if (userInfo==null){
                return Response.fail("501","账号不存在");
            }
            //用户该账户在数据库的密码
            String userPwd = userInfo.getUserPwd();
            //用户输入的密码
            String inputPwd = SecureUtil.md5(userInfoVo.getUserPwd());
            if (!inputPwd.equals(userPwd)){
                return Response.fail("501","账号或者密码错误");
            }
            //颁发令牌
            return Response.success("200","登录成功","aaa");
        }
    }
