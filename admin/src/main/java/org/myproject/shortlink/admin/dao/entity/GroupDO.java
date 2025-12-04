package org.myproject.shortlink.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_group")
public class GroupDO {
    /** 自增主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 分组业务ID（对外暴露，非自增ID） */
    private String gid;

    /** 分组名称 */
    private String name;

    /** 用户名（多租户隔离依据） */
    private String username;

    /** 分组排序值 */
    private Integer sortOrder;

    /** 创建时间（MP 自动填充） */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 修改时间（MP 自动填充） */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 删除标记（0=正常，1=删除） */
    @TableLogic
    private Integer delFlag;
}
