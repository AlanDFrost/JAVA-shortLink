package org.myproject.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.myproject.shortlink.admin.common.convention.result.Result;
import org.myproject.shortlink.admin.remote.ShortLinkRemoteService;
import org.myproject.shortlink.admin.remote.dto.request.ShortLinkGroupStatsAccessRecordReqDTO;
import org.myproject.shortlink.admin.remote.dto.request.ShortLinkStatsAccessRecordReqDTO;
import org.myproject.shortlink.admin.remote.dto.response.ShortLinkStatsAccessRecordRespDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShortLinkStatsController {
    private final ShortLinkRemoteService shortLinkRemoteService;
    /**
     * 记录单个短链接在给定时间内的访问监控记录
     * @return
     */
    @GetMapping("/api/short-link/admin/v1/stats/access-record")
    public Result<IPage<ShortLinkStatsAccessRecordRespDTO>> getShortLinkStatsAccessRecord (ShortLinkStatsAccessRecordReqDTO requestparam) {
        return shortLinkRemoteService.getShortLinkStatsAccessRecord(requestparam);
    }

    @GetMapping("/api/short-link/admin/v1/stats/access-record/group")
    public Result<IPage<ShortLinkStatsAccessRecordRespDTO>> groupShortLinkStatsAccessRecord(ShortLinkGroupStatsAccessRecordReqDTO requestParam) {
        return shortLinkRemoteService.groupShortLinkStatsAccessRecord(requestParam);
    }
}
