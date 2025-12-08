package org.myproject.shortlink.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_user")
public class UserDO extends BaseDO{
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("username")
    private String userName;
    private String password;
    private String realName;
    private String phone;
    private String mail;
    private LocalDateTime deletionTime;
}
