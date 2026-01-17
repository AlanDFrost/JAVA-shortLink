package org.myproject.shortlink.project.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.myproject.shortlink.project.dto.request.ShortLinkGroupStatsAccessRecordReqDTO;
import org.myproject.shortlink.project.dto.request.ShortLinkStatsAccessRecordReqDTO;
import org.myproject.shortlink.project.dto.response.ShortLinkStatsAccessRecordRespDTO;

public interface ShortLinkStatsService {
    /**
     * 查询单个短链接指定时间内的访问记录监控数据
     * @param requestParam
     * @return
     */
    Page<ShortLinkStatsAccessRecordRespDTO> shortLinkStatsAccessRecord(ShortLinkStatsAccessRecordReqDTO requestParam);

    /**
     * 查询分组短链接指定时间内的访问记录监控数据
     * @param requestParam
     * @return
     */
    Page<ShortLinkStatsAccessRecordRespDTO> groupShortLinkStatsAccessRecord(ShortLinkGroupStatsAccessRecordReqDTO requestParam);
}
