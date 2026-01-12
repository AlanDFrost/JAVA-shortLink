package org.myproject.shortlink.project.dto.request;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.myproject.shortlink.project.dao.entity.ShortLinkDO;

@Data
public class ShortLinkPageReqDTO extends Page<ShortLinkDO> {
    /*
    分组标识
     */
    String gid;

    /*
    排序方法
     */
    String orderTag;
}
