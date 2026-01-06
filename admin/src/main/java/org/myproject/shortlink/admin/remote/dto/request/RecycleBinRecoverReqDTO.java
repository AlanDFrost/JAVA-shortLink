package org.myproject.shortlink.admin.remote.dto.request;

import lombok.Data;

@Data
public class RecycleBinRecoverReqDTO {
    /** 所属分组ID（例如：groupId） */
    private String gid;

    /** 完整短链接（域名 + / + 短链接） */
    private String fullShortUrl;
}
