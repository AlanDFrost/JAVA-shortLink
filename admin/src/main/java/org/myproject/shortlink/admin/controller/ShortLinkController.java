package org.myproject.shortlink.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.myproject.shortlink.admin.common.convention.result.Result;
import org.myproject.shortlink.admin.common.convention.result.Results;
import org.myproject.shortlink.admin.remote.ShortLinkActualRemoteService;
import org.myproject.shortlink.admin.remote.dto.request.ShortLinkCreateReqDTO;
import org.myproject.shortlink.admin.remote.dto.request.ShortLinkPageReqDTO;
import org.myproject.shortlink.admin.remote.dto.request.ShortLinkUpdateReqDTO;
import org.myproject.shortlink.admin.remote.dto.response.ShortLinkCreateRespDTO;
import org.myproject.shortlink.admin.remote.dto.response.ShortLinkPageRespDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ShortLinkController {
    private final ShortLinkActualRemoteService shortLinkActualRemoteService;

    @PostMapping("/api/short-link/admin/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink (@RequestBody ShortLinkCreateReqDTO requestparam) {
        return shortLinkActualRemoteService.createShortLink(requestparam);
    }

    @GetMapping("/api/short-link/admin/v1/page")
    public Result<Page<ShortLinkPageRespDTO>> pageShortLink (ShortLinkPageReqDTO requestparam) {
        return shortLinkActualRemoteService.pageShortLink(requestparam.getGid(), requestparam.getOrderTag(), requestparam.getCurrent(), requestparam.getSize());
    }

    @PutMapping(("/api/short-link/admin/v1/update"))
    public Result<Void> updateShortLink (@RequestBody ShortLinkUpdateReqDTO requestparam) {
        shortLinkActualRemoteService.updateShortLink(requestparam);
        return Results.success();
    }
}
