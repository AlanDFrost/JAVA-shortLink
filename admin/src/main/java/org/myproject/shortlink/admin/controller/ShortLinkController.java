package org.myproject.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.myproject.shortlink.admin.common.convention.result.Result;
import org.myproject.shortlink.admin.common.convention.result.Results;
import org.myproject.shortlink.admin.remote.ShortLinkRemoteService;
import org.myproject.shortlink.admin.remote.dto.request.ShortLinkCreateReqDTO;
import org.myproject.shortlink.admin.remote.dto.request.ShortLinkPageReqDTO;
import org.myproject.shortlink.admin.remote.dto.request.ShortLinkUpdateReqDTO;
import org.myproject.shortlink.admin.remote.dto.response.ShortLinkCreateRespDTO;
import org.myproject.shortlink.admin.remote.dto.response.ShortLinkPageRespDTO;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShortLinkController {
    @PostMapping("/api/short-link/admin/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink (@RequestBody ShortLinkCreateReqDTO requestparam) {
        ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {};
        return shortLinkRemoteService.createShortLink(requestparam);
    }

    @GetMapping("/api/short-link/admin/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink (ShortLinkPageReqDTO requestparam) {
        ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {};
        return shortLinkRemoteService.pageShortLink(requestparam);
    }

    @PutMapping(("/api/short-link/admin/v1/update"))
    public Result<Void> updateShortLink (@RequestBody ShortLinkUpdateReqDTO requestparam) {
        ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {};
        shortLinkRemoteService.updateShortLink(requestparam);
        return Results.success();
    }
}
