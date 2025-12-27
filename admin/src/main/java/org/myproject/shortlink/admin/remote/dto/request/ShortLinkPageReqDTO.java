package org.myproject.shortlink.admin.remote.dto.request;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class ShortLinkPageReqDTO extends Page {
    /*
    分组标识
     */
    String gid;
}
