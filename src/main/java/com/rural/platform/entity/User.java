package com.rural.platform.entity;

import lombok.Data;
import lombok.ToString;
import java.time.LocalDateTime;

/**
 * user表的实体类
 */
@Data
@ToString
public class User {
    private Long id;
    private String userCode;
    private String userName;
    private String userPwd;
    private String userType;
    private String userState;
    private String isDelete;
    private String email;
    private String phone;
    private String address;
    private Long createBy;
    private LocalDateTime createTime;
    private Long updateBy;
    private LocalDateTime updateTime;
    private LocalDateTime createdAt;
    private String creatorCode;
    private String realName;
    private String gender;
    private String location;
    private String bio;
}
