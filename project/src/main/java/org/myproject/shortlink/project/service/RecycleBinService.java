package org.myproject.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.myproject.shortlink.project.dao.entity.ShortLinkDO;
import org.myproject.shortlink.project.dto.request.RecycleBinSaveReqDTO;
import org.myproject.shortlink.project.dto.request.ShortLinkPageReqDTO;
import org.myproject.shortlink.project.dto.response.ShortLinkPageRespDTO;

public interface RecycleBinService extends IService<ShortLinkDO> {
    /**
     * 短链接添加到回收站
     * @param requestParam
     */
    void saveRecycleBin(RecycleBinSaveReqDTO requestParam);

    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO requestparam);
}
