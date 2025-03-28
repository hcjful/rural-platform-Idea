package vo;

import lombok.Data;

@Data
public class UserInfoVo {
    private String userCode;//用户名

    private String userPwd;//密码

    private String verificationCode;//验证码
}
