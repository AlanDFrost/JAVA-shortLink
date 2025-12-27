package org.myproject.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.myproject.shortlink.admin.common.convention.result.Result;
import org.myproject.shortlink.admin.remote.ShortLinkRemoteService;
import org.myproject.shortlink.admin.remote.dto.request.ShortLinkCreateReqDTO;
import org.myproject.shortlink.admin.remote.dto.request.ShortLinkPageReqDTO;
import org.myproject.shortlink.admin.remote.dto.response.ShortLinkCreateRespDTO;
import org.myproject.shortlink.admin.remote.dto.response.ShortLinkPageRespDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
