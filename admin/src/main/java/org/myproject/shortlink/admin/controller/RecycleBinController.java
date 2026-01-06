package org.myproject.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.myproject.shortlink.admin.common.convention.result.Result;
import org.myproject.shortlink.admin.common.convention.result.Results;
import org.myproject.shortlink.admin.remote.ShortLinkRemoteService;
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
    private final ShortLinkRemoteService shortLinkRemoteService;

    @PostMapping("/api/short-link/v1/admin/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO requestParam) {
        shortLinkRemoteService.saveRecycleBin(requestParam);
        return Results.success();
    }

    @GetMapping("/api/short-link/v1/admin/recycle-bin/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageRecycleBin (ShortLinkPageReqDTO requestparam) {
        return shortLinkRemoteService.pageRecycleBin(requestparam);
    }
}
