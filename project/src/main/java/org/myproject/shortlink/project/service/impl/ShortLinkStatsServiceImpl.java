package org.myproject.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.myproject.shortlink.project.dao.entity.LinkAccessLogsDO;
import org.myproject.shortlink.project.dao.mapper.LinkStatsAccessRecordMapper;
import org.myproject.shortlink.project.dto.request.ShortLinkGroupStatsAccessRecordReqDTO;
import org.myproject.shortlink.project.dto.request.ShortLinkStatsAccessRecordReqDTO;
import org.myproject.shortlink.project.dto.response.ShortLinkStatsAccessRecordRespDTO;
import org.myproject.shortlink.project.service.ShortLinkStatsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShortLinkStatsServiceImpl implements ShortLinkStatsService {
    private final LinkStatsAccessRecordMapper linkStatsAccessRecordMapper;

    @Override
    public Page<ShortLinkStatsAccessRecordRespDTO> shortLinkStatsAccessRecord(ShortLinkStatsAccessRecordReqDTO requestParam) {
        LambdaQueryWrapper<LinkAccessLogsDO> wrapper = Wrappers.lambdaQuery(LinkAccessLogsDO.class)
                .eq(LinkAccessLogsDO::getGid, requestParam.getGid())
                .eq(LinkAccessLogsDO::getFullShortUrl, requestParam.getFullShortUrl())
                .eq(LinkAccessLogsDO::getDelFlag, 0);
        IPage<LinkAccessLogsDO> LinkAccessLogsDOIPage = linkStatsAccessRecordMapper.selectPage(requestParam, wrapper);
        IPage<ShortLinkStatsAccessRecordRespDTO> actualIpageResult = LinkAccessLogsDOIPage.convert(each -> BeanUtil.toBean(each, ShortLinkStatsAccessRecordRespDTO.class));

        Page<ShortLinkStatsAccessRecordRespDTO> actualResult = new Page<>();
        actualResult.setCurrent(actualIpageResult.getCurrent());
        actualResult.setSize(actualIpageResult.getSize());
        actualResult.setTotal(actualIpageResult.getTotal());
        actualResult.setPages(actualIpageResult.getPages());
        actualResult.setRecords(actualIpageResult.getRecords());

        List<String> userAccessLogsList = actualResult.getRecords()
                .stream()
                .map(ShortLinkStatsAccessRecordRespDTO::getUser)
                .toList();
        List<Map<String, Object>> uvTypeList= linkStatsAccessRecordMapper.selectUvTypeByUsers(requestParam, userAccessLogsList);
        actualResult.getRecords().forEach(each -> {
            String uvType = uvTypeList.stream()
                    .filter(item -> Objects.equals(each.getUser(), item.get("user")))
                    .findFirst()
                    .map(item -> item.get("uvType"))
                    .map(Object::toString)
                    .orElse("旧访客");
            each.setUvType((uvType));
        });
        return actualResult;
    }

    @Override
    public Page<ShortLinkStatsAccessRecordRespDTO> groupShortLinkStatsAccessRecord(ShortLinkGroupStatsAccessRecordReqDTO requestParam) {
        LambdaQueryWrapper<LinkAccessLogsDO> wrapper = Wrappers.lambdaQuery(LinkAccessLogsDO.class)
                .eq(LinkAccessLogsDO::getGid, requestParam.getGid())
                .eq(LinkAccessLogsDO::getDelFlag, 0);
        IPage<LinkAccessLogsDO> LinkAccessLogsDOIPage = linkStatsAccessRecordMapper.selectPage(requestParam, wrapper);
        IPage<ShortLinkStatsAccessRecordRespDTO> actualIpageResult = LinkAccessLogsDOIPage.convert(each -> BeanUtil.toBean(each, ShortLinkStatsAccessRecordRespDTO.class));

        Page<ShortLinkStatsAccessRecordRespDTO> actualResult = new Page<>();
        actualResult.setCurrent(actualIpageResult.getCurrent());
        actualResult.setSize(actualIpageResult.getSize());
        actualResult.setTotal(actualIpageResult.getTotal());
        actualResult.setPages(actualIpageResult.getPages());
        actualResult.setRecords(actualIpageResult.getRecords());

        List<String> userAccessLogsList = actualResult.getRecords()
                .stream()
                .map(ShortLinkStatsAccessRecordRespDTO::getUser)
                .toList();
        List<Map<String, Object>> uvTypeList= linkStatsAccessRecordMapper.selectGroupUvTypeByUsers(requestParam, userAccessLogsList);
        actualResult.getRecords().forEach(each -> {
            String uvType = uvTypeList.stream()
                    .filter(item -> Objects.equals(each.getUser(), item.get("user")))
                    .findFirst()
                    .map(item -> item.get("uvType"))
                    .map(Object::toString)
                    .orElse("旧访客");
            each.setUvType((uvType));
        });
        return actualResult;
    }
}
