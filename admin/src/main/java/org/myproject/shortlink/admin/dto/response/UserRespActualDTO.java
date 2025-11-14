package org.myproject.shortlink.admin.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

/*
 * 用户返回无脱敏的参数响应
 * */
@Data
public class UserRespActualDTO {
    private Long id;
    private String userName;
    private String realName;
    private String phone;
    private String mail;
    private LocalDateTime deletionTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean deleteFlag;

    @Override
    public String toString() {
        return "UserDO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", realName='" + realName + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", deletionTime=" + deletionTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteFlag=" + deleteFlag +
                '}';
    }
}