package org.myproject.shortlink.project.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.myproject.shortlink.project.dao.entity.ShortLinkDO;
import org.myproject.shortlink.project.dto.request.RecycleBinRecoverReqDTO;
import org.myproject.shortlink.project.dto.request.RecycleBinRemoveReqDTO;
import org.myproject.shortlink.project.dto.request.RecycleBinSaveReqDTO;
import org.myproject.shortlink.project.dto.request.ShortLinkPageReqDTO;
import org.myproject.shortlink.project.dto.response.ShortLinkPageRespDTO;

public interface RecycleBinService extends IService<ShortLinkDO> {
    /**
     * 短链接添加到回收站
     * @param requestParam
     */
    void saveRecycleBin(RecycleBinSaveReqDTO requestParam);

    Page<ShortLinkPageRespDTO> pageRecycleBin(ShortLinkPageReqDTO requestparam);

    /**
     * 恢复短链接
     * @param requestParam
     */
    void recoverRecycleBin(RecycleBinRecoverReqDTO requestParam);

    /**
     * 从回收站彻底删除短链接
     * @param requestParam
     */
    void removeRecycleBin(RecycleBinRemoveReqDTO requestParam);
}
