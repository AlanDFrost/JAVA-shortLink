package org.myproject.shortlink.project.dto.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShortLinkPageRespDTO {
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
    private Integer clickNum;

    /** 所属分组ID（例如：groupId） */
    private String gid;

    /** 有效期类型：0 永久、1 自定义 */
    private Integer validDateType;

    /** 有效期时间 */
    private LocalDateTime validDate;

    /** 描述信息 */
    @TableField("`describe`")
    private String describe;

    /** 图标 */
    private String favicon;
}
