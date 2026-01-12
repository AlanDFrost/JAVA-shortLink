package org.myproject.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.myproject.shortlink.project.common.convention.result.Result;
import org.myproject.shortlink.project.common.convention.result.Results;
import org.myproject.shortlink.project.dto.request.ShortLinkStatsAccessRecordReqDTO;
import org.myproject.shortlink.project.dto.response.ShortLinkStatsAccessRecordRespDTO;
import org.myproject.shortlink.project.service.ShortLinkStatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShortLinkStatsController {
    private final ShortLinkStatsService shortLinkStatsService;
    /**
     * 记录单个短链接在给定时间内的访问监控记录
     * @return
     */
    @GetMapping("/api/short-link/project/v1/stats/access-record")
    public Result<IPage<ShortLinkStatsAccessRecordRespDTO>> getShortLinkStatsAccessRecord(ShortLinkStatsAccessRecordReqDTO requestParam) {
        return Results.success(shortLinkStatsService.getShortLinkStatsAccessRecord(requestParam));
    }
}
