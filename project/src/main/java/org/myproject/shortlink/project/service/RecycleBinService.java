package org.myproject.shortlink.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.myproject.shortlink.project.dao.entity.ShortLinkDO;
import org.myproject.shortlink.project.dto.request.RecycleBinSaveReqDTO;

public interface RecycleBinService extends IService<ShortLinkDO> {
    /**
     * 短链接添加到回收站
     * @param requestParam
     */
    void saveRecycleBin(RecycleBinSaveReqDTO requestParam);
}
