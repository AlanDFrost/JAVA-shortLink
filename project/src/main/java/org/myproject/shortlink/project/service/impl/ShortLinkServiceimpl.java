package org.myproject.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.myproject.shortlink.project.dao.entity.ShortLinkDO;
import org.myproject.shortlink.project.dao.mapper.ShortLinkMapper;
import org.myproject.shortlink.project.dto.request.ShortLinkCreateReqDTO;
import org.myproject.shortlink.project.dto.response.ShortLinkCreateRespDTO;
import org.myproject.shortlink.project.service.ShortLinkService;
import org.myproject.shortlink.project.toolkit.HashUtil;
import org.springframework.stereotype.Service;

/*
短链接接口实现层
 */
@Slf4j
@Service
public class ShortLinkServiceimpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements ShortLinkService {

    @Override
    public ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO requestparam) {
        String shortLinkSuffix = generateSuffix(requestparam);
        ShortLinkDO shortLinkDO = BeanUtil.toBean(requestparam, ShortLinkDO.class);
        shortLinkDO.setShortUri(shortLinkSuffix);
        shortLinkDO.setFullShortUrl(requestparam.getDomain() + "/" + shortLinkSuffix);

        baseMapper.insert(shortLinkDO);
        return ShortLinkCreateRespDTO.builder()
                .fullShortUrl(shortLinkDO.getFullShortUrl())
                .originUrl(requestparam.getOriginUrl())
                .gid(requestparam.getGid())
                .build();
    }

    private String generateSuffix(ShortLinkCreateReqDTO requestparam) {
        String originalUrl = requestparam.getOriginUrl();
        return HashUtil.hashToBase62(originalUrl);
    }
}
