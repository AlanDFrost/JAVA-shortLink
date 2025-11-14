package org.myproject.shortlink.admin.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

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
    private LocalDateTime deletionTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean delFlag;
}
