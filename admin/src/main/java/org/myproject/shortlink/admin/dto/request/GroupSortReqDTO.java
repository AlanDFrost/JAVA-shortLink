package org.myproject.shortlink.admin.dto.request;

import lombok.Data;

@Data
public class GroupSortReqDTO {
    /*
    分组id
     */
    String gid;

    /*
    分组顺序
     */
    Integer sortOrder;
}
