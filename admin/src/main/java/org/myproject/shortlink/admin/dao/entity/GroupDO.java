package org.myproject.shortlink.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("t_group")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupDO extends BaseDO{
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
}
