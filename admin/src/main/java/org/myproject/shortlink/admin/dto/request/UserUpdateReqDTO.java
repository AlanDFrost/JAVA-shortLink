package org.myproject.shortlink.admin.dto.request;

import lombok.Data;

@Data
public class UserUpdateReqDTO {
    private String userName;
    private String password;
    private String realName;
    private String phone;
    private String mail;
}
