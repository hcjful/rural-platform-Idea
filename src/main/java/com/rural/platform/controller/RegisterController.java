package com.rural.platform.controller;

import com.rural.platform.dto.RegisterRequest;
import com.rural.platform.entity.User;
import com.rural.platform.response.Response;
import com.rural.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class RegisterController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册接口
     * @param request 注册请求参数
     * @return 注册结果
     */
    @PostMapping("/register")
    public Response<User> register(@Valid @RequestBody RegisterRequest request) {
        try {
            // 参数校验
            if (request.getUserCode() == null || request.getUserCode().trim().isEmpty()) {
                return Response.fail("501", "账号不能为空");
            }
            if (request.getUserName() == null || request.getUserName().trim().isEmpty()) {
                return Response.fail("501", "用户名不能为空");
            }
            if (request.getUserPwd() == null || request.getUserPwd().trim().isEmpty()) {
                return Response.fail("501", "密码不能为空");
            }
            if (request.getUserPwd().length() < 6) {
                return Response.fail("501", "密码长度不能小于6位");
            }

            // 调用服务层进行注册
            User user = userService.register(request);
            
            // 注册成功
            return Response.success("200", "注册成功", user);
        } catch (RuntimeException e) {
            // 捕获业务异常
            return Response.fail("501", e.getMessage());
        } catch (Exception e) {
            // 捕获其他异常
            e.printStackTrace();
            return Response.fail("500", "注册失败，请稍后重试");
        }
    }

    /**
     * 检查账号是否已存在
     * @param userCode 账号
     * @return 检查结果
     */
    @GetMapping("/check-usercode")
    public Response<Boolean> checkUserCode(@RequestParam String userCode) {
        try {
            boolean exists = userService.checkUserExists(userCode);
            return Response.success("200", exists ? "账号已存在" : "账号可用", exists);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("500", "检查账号失败，请稍后重试");
        }
    }

    /**
     * 检查邮箱是否已存在
     * @param email 邮箱
     * @return 检查结果
     */
    @GetMapping("/check-email")
    public Response<Boolean> checkEmail(@RequestParam String email) {
        try {
            boolean exists = userService.checkEmailExists(email);
            return Response.success("200", exists ? "邮箱已被使用" : "邮箱可用", exists);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("500", "检查邮箱失败，请稍后重试");
        }
    }

    /**
     * 检查手机号是否已存在
     * @param phone 手机号
     * @return 检查结果
     */
    @GetMapping("/check-phone")
    public Response<Boolean> checkPhone(@RequestParam String phone) {
        try {
            boolean exists = userService.checkPhoneExists(phone);
            return Response.success("200", exists ? "手机号已被使用" : "手机号可用", exists);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("500", "检查手机号失败，请稍后重试");
        }
    }
}
