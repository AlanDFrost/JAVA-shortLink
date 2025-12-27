package org.myproject.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.myproject.shortlink.project.common.convention.result.Result;
import org.myproject.shortlink.project.common.convention.result.Results;
import org.myproject.shortlink.project.dto.request.ShortLinkCreateReqDTO;
import org.myproject.shortlink.project.dto.request.ShortLinkPageReqDTO;
import org.myproject.shortlink.project.dto.response.ShortLinkCreateRespDTO;
import org.myproject.shortlink.project.dto.response.ShortLinkPageRespDTO;
import org.myproject.shortlink.project.service.ShortLinkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShortLinkController {
    private final ShortLinkService shortLinkService;

    @PostMapping("/api/short-link/project/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink (@RequestBody ShortLinkCreateReqDTO requestparam) {
        return Results.success(shortLinkService.createShortLink(requestparam));
    }

    @GetMapping("/api/short-link/project/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink (ShortLinkPageReqDTO requestparam) {
        return Results.success(shortLinkService.pageShortLink(requestparam));
    }
}
