package org.myproject.shortlink.admin.remote;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.myproject.shortlink.admin.common.convention.result.Result;
import org.myproject.shortlink.admin.remote.dto.request.*;
import org.myproject.shortlink.admin.remote.dto.response.ShortLinkCreateRespDTO;
import org.myproject.shortlink.admin.remote.dto.response.ShortLinkGroupCountQueryRespDTO;
import org.myproject.shortlink.admin.remote.dto.response.ShortLinkPageRespDTO;
import org.myproject.shortlink.admin.remote.dto.response.ShortLinkStatsAccessRecordRespDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

/**
 * 短链接中台远程调用服务
 */
@FeignClient("short-link-project")
public interface ShortLinkActualRemoteService {

    /** 后管调用中台创建短链接 */
    @PostMapping("/api/short-link/project/v1/create")
    Result<ShortLinkCreateRespDTO> createShortLink (@RequestBody ShortLinkCreateReqDTO requestparam);

    /** 修改分组中的短链接 */
    @PostMapping("/api/short-link/project/v1/update")
    void updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestparam);

    /** 后管调用中台查询短链接分组 */
    @GetMapping("/api/short-link/project/v1/page")
    Result<Page<ShortLinkPageRespDTO>> pageShortLink (@RequestParam("gid") String gid, @RequestParam("orderTag") String orderTag,
            @RequestParam("current") Long current,
            @RequestParam("size") Long size);

    /** 查询分组中的短链接数量 */
    @GetMapping("/api/short-link/project/v1/count")
    Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(List<String> gids);

    /** 短链接删除至回收站 */
    @PostMapping("/api/short-link/project/v1/recycle-bin/save")
    void saveRecycleBin(@RequestBody RecycleBinSaveReqDTO requestParam);

    /** 查询回收站里的短链接 */
    @GetMapping("/api/short-link/project/v1/recycle-bin/page")
    Result<Page<ShortLinkPageRespDTO>> pageRecycleBin (@RequestParam("gid") String gid, @RequestParam("orderTag")String orderTag,
                                                       @RequestParam("current") Long current,
                                                       @RequestParam("size")Long size);

    /** 从回收站恢复短链接 */
    @PostMapping("/api/short-link/project/v1/recycle-bin/recover")
    void recoverRecycleBin(@RequestBody  RecycleBinRecoverReqDTO requestParam);

    /** 从回收站彻底移除短链接 */
    @PostMapping("/api/short-link/project/v1/recycle-bin/remove")
    void removeRecycleBin(@RequestBody RecycleBinRemoveReqDTO requestParam);

    /** 分页查询单个短链接访问日志 */
    @GetMapping("/api/short-link/project/v1/stats/access-record")
    Result<Page<ShortLinkStatsAccessRecordRespDTO>> getShortLinkStatsAccessRecord(@RequestParam("fullShortUrl") String fullShortUrl,
                                                                                  @RequestParam("gid") String gid,
                                                                                  @RequestParam("beginDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginDate,
                                                                                  @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                                                                  @RequestParam("current") Long current,
                                                                                  @RequestParam("size") Long size);

    /** 访问分组短链接的监控日志 */
    @GetMapping("/api/short-link/project/v1/stats/access-record/group")
    Result<Page<ShortLinkStatsAccessRecordRespDTO>> groupShortLinkStatsAccessRecord(@RequestParam("gid") String gid,
                                                                                            @RequestParam("beginDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginDate,
                                                                                            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                                                                            @RequestParam("current") Long current,
                                                                                            @RequestParam("size") Long size);
}
