package org.myproject.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.myproject.shortlink.project.dao.entity.ShortLinkDO;
import org.myproject.shortlink.project.dto.request.ShortLinkCreateReqDTO;
import org.myproject.shortlink.project.dto.request.ShortLinkPageReqDTO;
import org.myproject.shortlink.project.dto.request.ShortLinkUpdateReqDTO;
import org.myproject.shortlink.project.dto.response.ShortLinkCreateRespDTO;
import org.myproject.shortlink.project.dto.response.ShortLinkGroupCountQueryRespDTO;
import org.myproject.shortlink.project.dto.response.ShortLinkPageRespDTO;

import java.util.List;

/*
短链接接口层
 */
public interface ShortLinkService extends IService<ShortLinkDO> {
    /*
    创建短链接
     */
    ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO requestparam);

    /*
    更新短链接
     */
    void updateShortLink(ShortLinkUpdateReqDTO requestparam);

    /*
    分页查询短链接请求参数
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO requestparam);

    /*
    查询短链接分组内有多少条短链接
     */
    List<ShortLinkGroupCountQueryRespDTO> listGroupShortLinkCount(List<String> gids);
}
