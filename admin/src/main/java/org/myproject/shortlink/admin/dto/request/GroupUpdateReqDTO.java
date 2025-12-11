package org.myproject.shortlink.admin.dto.request;

import lombok.Data;

@Data
public class GroupUpdateReqDTO {
    /*
    分组id
     */
    public String gid;

    /*
    分组名
     */
    public String groupName;
}
