package org.myproject.shortlink.admin.remote.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShortLinkCreateReqDTO {
    /** 域名 */
    private String domain;

    /** 原始长链接 */
    private String originUrl;

    /** 所属分组ID（例如：groupId） */
    private String gid;

    /** 创建方式：0 接口创建、1 控制台创建 */
    private Integer createdType;

    /** 有效期类型：0 永久、1 自定义 */
    private Integer validDateType;

    /** 有效期时间 */
    private LocalDateTime validDate;

    /** 描述信息 */
    private String describe;

    /** 图标 */
    private String favicon;
}
