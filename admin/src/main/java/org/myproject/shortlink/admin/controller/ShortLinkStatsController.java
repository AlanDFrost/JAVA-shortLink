package org.myproject.shortlink.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.myproject.shortlink.admin.common.convention.result.Result;
import org.myproject.shortlink.admin.remote.ShortLinkActualRemoteService;
import org.myproject.shortlink.admin.remote.dto.request.ShortLinkGroupStatsAccessRecordReqDTO;
import org.myproject.shortlink.admin.remote.dto.request.ShortLinkStatsAccessRecordReqDTO;
import org.myproject.shortlink.admin.remote.dto.response.ShortLinkStatsAccessRecordRespDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShortLinkStatsController {
    private final ShortLinkActualRemoteService shortLinkActualRemoteService;
    /**
     * 记录单个短链接在给定时间内的访问监控记录
     * @return
     */
    @GetMapping("/api/short-link/admin/v1/stats/access-record")
    public Result<Page<ShortLinkStatsAccessRecordRespDTO>> getShortLinkStatsAccessRecord (ShortLinkStatsAccessRecordReqDTO requestparam) {
        return shortLinkActualRemoteService.getShortLinkStatsAccessRecord(requestparam.getFullShortUrl(), requestparam.getGid(),
                requestparam.getBeginDate(), requestparam.getEndDate(), requestparam.getCurrent(), requestparam.getSize());
    }

    @GetMapping("/api/short-link/admin/v1/stats/access-record/group")
    public Result<Page<ShortLinkStatsAccessRecordRespDTO>> groupShortLinkStatsAccessRecord(ShortLinkGroupStatsAccessRecordReqDTO requestParam) {
        return shortLinkActualRemoteService.groupShortLinkStatsAccessRecord(requestParam.getGid(), requestParam.getBeginDate(),
                requestParam.getEndDate(), requestParam.getCurrent(), requestParam.getSize());
    }
}
