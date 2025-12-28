package org.myproject.shortlink.admin.remote.dto.response;

import lombok.Data;

/**
 * 短链接分组查询返回参数
 */
@Data
public class ShortLinkGroupCountQueryRespDTO {
    /** 组id */
    private String gid;

    /** 组内短链接数量 */
    private Integer shortLinkCount;
}
