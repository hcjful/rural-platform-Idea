package com.rural.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDateTime;

/**
 * user_info表的实体类
 */
@Data
@ToString
@TableName("users")
public class User {
    private Long id;
    private String userCode;
    private String userName;
    private String realName;
    private String userPwd;
    private String gender;
    private String phone;
    private String email;
    private String avatar;
    private String location;
    private String bio;
    private Integer postCount;
    private Integer followCount;
    private Integer fansCount;
    private String role;
    private String userType;
    private String userState;
    private String isDelete;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public User() {}

    public User(String userCode, String userName, String userPwd, String email, String phone) {
        this.userCode = userCode;
        this.userName = userName;
        this.userPwd = userPwd;
        this.email = email;
        this.phone = phone;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
}
