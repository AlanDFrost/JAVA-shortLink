package org.myproject.shortlink.admin.dto.request;

import lombok.Data;

/*
用户注册请求实体
 */
@Data
public class UserRegisterReqDTO {
    private Long id;
    private String userName;
    private String password;
    private String realName;
    private String phone;
    private String mail;
}
