package org.myproject.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.myproject.shortlink.admin.dao.entity.GroupDO;

public interface GroupService extends IService<GroupDO> {
    // 新增短链接分组
    void saveGroup(String groupName);

    // gid是否已存在
    boolean hasGid(String gid);
}
