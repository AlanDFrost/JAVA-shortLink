package org.myproject.shortlink.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.myproject.shortlink.project.dao.entity.ShortLinkDO;
import org.myproject.shortlink.project.dto.request.ShortLinkCreateReqDTO;
import org.myproject.shortlink.project.dto.response.ShortLinkCreateRespDTO;

/*
短链接接口层
 */
public interface ShortLinkService extends IService<ShortLinkDO> {
    /*
    创建短链接
     */
    ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO requestparam);
}
