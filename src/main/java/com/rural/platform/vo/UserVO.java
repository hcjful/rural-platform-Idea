package com.rural.platform.vo;

import lombok.Data;

@Data
public class UserVO {
    private Long id;
    private String userCode;
    private String userName;
    private String realName;
    private String email;
    private String phone;
    private String avatar;
    private String role;
    private Integer postCount;
    private Integer followCount;
    private Integer fansCount;
    private String location;
    private String bio;
}
