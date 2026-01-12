package org.myproject.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.myproject.shortlink.project.dto.request.ShortLinkStatsAccessRecordReqDTO;
import org.myproject.shortlink.project.dto.response.ShortLinkStatsAccessRecordRespDTO;

public interface ShortLinkStatsService {
    IPage<ShortLinkStatsAccessRecordRespDTO> getShortLinkStatsAccessRecord(ShortLinkStatsAccessRecordReqDTO requestParam);
}
