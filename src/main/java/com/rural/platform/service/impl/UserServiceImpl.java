package com.rural.platform.service.impl;

import com.rural.platform.entity.User;
import com.rural.platform.mapper.UserMapper;
import com.rural.platform.response.Response;
import com.rural.platform.service.UserService;
import com.rural.platform.utils.CacheUtil;
import com.rural.platform.utils.DigestUtil;
import com.rural.platform.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rural.platform.dto.RegisterRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public User register(RegisterRequest request) {
        // 检查用户是否已存在
        if (checkUserExists(request.getUserCode())) {
            throw new RuntimeException("账号已存在");
        }
        if (request.getEmail() != null && checkEmailExists(request.getEmail())) {
            throw new RuntimeException("邮箱已被使用");
        }
        if (request.getPhone() != null && checkPhoneExists(request.getPhone())) {
            throw new RuntimeException("手机号已被使用");
        }

        // 创建新用户
        User user = new User();
        user.setUserCode(request.getUserCode());
        user.setUserName(request.getUserName());
        // 使用DigestUtil进行MD5加密
        user.setUserPwd(DigestUtil.hmacSign(request.getUserPwd()));
        user.setUserType("NORMAL");
        user.setUserState("ACTIVE");
        user.setIsDelete("0");
        user.setCreateTime(LocalDateTime.now());
        // 设置 email 和 phone 字段
        user.setEmail(request.getEmail()); // 设置 email
        user.setPhone(request.getPhone()); // 设置 phone
        // 保存用户
        userMapper.insert(user);
        
        return user;
    }

    @Override
    public boolean checkUserExists(String userCode) {
        return userMapper.countByUserCode(userCode) > 0;
    }

    @Override
    public boolean checkEmailExists(String email) {
        return userMapper.countByEmail(email) > 0;
    }

    @Override
    public boolean checkPhoneExists(String phone) {
        return userMapper.countByPhone(phone) > 0;
    }
    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }
    @Override
    public Response<String> login(UserInfoVo userInfoVo) {
        // 判断验证码是否正确
        String code = CacheUtil.captchaMap.get("127.0.0.1");
        System.out.println("从缓存获取验证码: Code=" + code);
        if (!code.equalsIgnoreCase(userInfoVo.getVerificationCode())) {
            return Response.fail("501","验证码错误");
        }
        //判断账号是否存在
        User user = userMapper.selectUserByCode(userInfoVo.getUserCode());
        if (user==null){
            return Response.fail("501","账号不存在");
        }
        //用户该账户在数据库的密码
        String userPwd = user.getUserPwd();
        //用户输入的密码进行加密
        String inputPwd = DigestUtil.hmacSign(userInfoVo.getUserPwd());
        if (!inputPwd.equals(userPwd)){
            return Response.fail("501","账号或者密码错误");
        }
        //颁发令牌
        return Response.success("200","登录成功","aaa");
    }
}