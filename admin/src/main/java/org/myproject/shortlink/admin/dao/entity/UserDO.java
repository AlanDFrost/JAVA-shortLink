package org.myproject.shortlink.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_user")
public class UserDO {
    private Long id;
    @TableField("username")
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
