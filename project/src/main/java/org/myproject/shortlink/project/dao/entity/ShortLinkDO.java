package org.myproject.shortlink.project.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 短链接实体类，对应表：t_link
 */
@Data
@TableName("t_link")  // ← 请改成你的实际表名
public class ShortLinkDO extends BaseDO{

    /** 主键ID，自增 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 域名 */
    private String domain;

    /** 短链接后缀（例如 A3x9k） */
    private String shortUri;

    /** 完整短链接（域名 + / + 短链接） */
    private String fullShortUrl;

    /** 原始长链接 */
    private String originUrl;

    /** 点击量 */
    private Integer clickNumber;

    /** 所属分组ID（例如：groupId） */
    private String gid;

    /** 启用状态：0 启用、1 未启用 */
    private Integer enableStatus;

    /** 创建方式：0 接口创建、1 控制台创建 */
    private Integer createdType;

    /** 有效期类型：0 永久、1 自定义 */
    private Integer validDateType;

    /** 有效期时间 */
    private LocalDateTime validDate;

    /** 描述信息 */
    @TableField("`describe`")
    private String describe;
}
