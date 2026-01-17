package org.myproject.shortlink.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.myproject.shortlink.admin.common.convention.result.Result;
import org.myproject.shortlink.admin.common.convention.result.Results;
import org.myproject.shortlink.admin.remote.ShortLinkActualRemoteService;
import org.myproject.shortlink.admin.remote.dto.request.RecycleBinRecoverReqDTO;
import org.myproject.shortlink.admin.remote.dto.request.RecycleBinRemoveReqDTO;
import org.myproject.shortlink.admin.remote.dto.request.RecycleBinSaveReqDTO;
import org.myproject.shortlink.admin.remote.dto.request.ShortLinkPageReqDTO;
import org.myproject.shortlink.admin.remote.dto.response.ShortLinkPageRespDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RecycleBinController {
    private final ShortLinkActualRemoteService shortLinkActualRemoteService;

    @PostMapping("/api/short-link/admin/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO requestParam) {
        shortLinkActualRemoteService.saveRecycleBin(requestParam);
        return Results.success();
    }

    @GetMapping("/api/short-link/admin/v1/recycle-bin/page")
    public Result<Page<ShortLinkPageRespDTO>> pageRecycleBin (ShortLinkPageReqDTO requestparam) {
        return shortLinkActualRemoteService.pageRecycleBin(requestparam.getGid(), requestparam.getOrderTag(), requestparam.getCurrent(), requestparam.getSize());
    }

    @PostMapping("/api/short-link/admin/v1/recycle-bin/recover")
    public Result<Void> recoverRecycleBin(@RequestBody RecycleBinRecoverReqDTO requestParam) {
        shortLinkActualRemoteService.recoverRecycleBin(requestParam);
        return Results.success();
    }

    @PostMapping("/api/short-link/admin/v1/recycle-bin/remove")
    public Result<Void> removeRecycleBin(@RequestBody RecycleBinRemoveReqDTO requestParam) {
        shortLinkActualRemoteService.removeRecycleBin(requestParam);
        return Results.success();
    }
}
