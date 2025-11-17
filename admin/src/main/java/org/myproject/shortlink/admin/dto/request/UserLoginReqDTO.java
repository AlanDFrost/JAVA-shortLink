package org.myproject.shortlink.admin.dto.request;

import lombok.Data;

/*
用户登录实体
 */
@Data
public class UserLoginReqDTO {
    /*
    一般需要三个参数：用户名、密码、验证码，这里我们只需要两个参数
     */
    private String username;
    private String password;
}
