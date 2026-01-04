package org.myproject.shortlink.admin.remote.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShortLinkUpdateReqDTO {
    /** 原始长链接 */
    private String originUrl;

    /** 完整短链接（域名 + / + 短链接） */
    private String fullShortUrl;

    /** 所属分组ID（例如：groupId） */
    private String gid;

    /** 有效期类型：0 永久、1 自定义 */
    private Integer validDateType;

    /** 有效期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime validDate;

    /** 描述信息 */
    private String describe;

    /** 图标 */
    private String favicon;
}
