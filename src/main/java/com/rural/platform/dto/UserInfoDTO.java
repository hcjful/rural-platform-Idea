package com.rural.platform.dto;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserInfoDTO {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 20, message = "用户名长度必须在2-20位之间")
    private String userName;
    
    @Size(max = 20, message = "真实姓名长度不能超过20位")
    private String realName;
    
    @Pattern(regexp = "^(男|女)$", message = "性别只能是男或女")
    private String gender;
    
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    
    @Email(message = "邮箱格式不正确")
    private String email;
    
    @Size(max = 100, message = "地址长度不能超过100位")
    private String location;
    
    @Size(max = 200, message = "个人简介不能超过200字")
    private String bio;
}
