package org.myproject.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.myproject.shortlink.admin.dao.entity.GroupDO;
import org.myproject.shortlink.admin.dto.request.GroupSortReqDTO;
import org.myproject.shortlink.admin.dto.response.GroupSearchRespDTO;

import java.util.List;

public interface GroupService extends IService<GroupDO> {
    // 新增短链接分组
    void saveGroup(String groupName);

    // gid是否已存在
    boolean hasGid(String gid);

    // 查询用户短链接分组集合
    List<GroupSearchRespDTO> listGroup();

    // 根据分组id和分组名修改分组名
    void updateGroup(String gid, String groupName);

    // 根据分组id删除分组
    void deleteGroup(String gid);

    // 短链接分组排序
    void sortGroup(List<GroupSortReqDTO> requestParam);
}
