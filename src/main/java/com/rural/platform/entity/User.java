package com.rural.platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDateTime;

/**
 * user_info表的实体类
 */
@Data
@ToString
public class User {
    private Long id;
    private String userName;    // 用户名
    private String nickName;    // 昵称
    private String userCode;    // 账号
    private String userPwd;     // 用户密码
    private String phone;       // 手机号
    private String email;       // 邮箱
    private String address;     // 地址
    private String userType;    // 用户类型
    private String userState;   // 用户状态
    private String isDelete;    // 删除状态
    private Integer createBy;   // 创建人id
    private Integer updateBy;   // 修改人id
    private String creatorCode; // 创建人编码
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 修改时间

    public User() {}

    public User(String userCode, String userName, String userPwd,
                String userType, String userState, String isDelete, String email, String phone,
                Integer createBy, LocalDateTime createTime, Integer updateBy, LocalDateTime updateTime) {
        this.userCode = userCode;
        this.userName = userName;
        this.userPwd = userPwd;
        this.userType = userType;
        this.userState = userState;
        this.isDelete = isDelete;
        this.email = email;
        this.phone = phone;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
    }
}
