package org.myproject.shortlink.admin.remote.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortLinkCreateRespDTO {
    /** 分组信息 */
    private String gid;

    /** 完整短链接（域名 + / + 短链接） */
    private String fullShortUrl;

    /** 原始长链接 */
    private String originUrl;

    /** 图标 */
    private String favicon;
}
