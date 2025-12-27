package org.myproject.shortlink.admin.dto.response;

import lombok.Data;

@Data
public class GroupSearchRespDTO {
    /** 分组业务ID（对外暴露，非自增ID） */
    private String gid;

    /** 分组名称 */
    private String name;

    /** 分组排序值 */
    private Integer sortOrder;

    private Integer shortLinkCount;
}
