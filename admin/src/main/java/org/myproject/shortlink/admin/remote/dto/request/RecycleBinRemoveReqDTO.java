package org.myproject.shortlink.admin.remote.dto.request;

import lombok.Data;

/**
 * 短链接从回收站彻底删除DTO
 */
@Data
public class RecycleBinRemoveReqDTO {
    /** 所属分组ID（例如：groupId） */
    private String gid;

    /** 完整短链接（域名 + / + 短链接） */
    private String fullShortUrl;
}
