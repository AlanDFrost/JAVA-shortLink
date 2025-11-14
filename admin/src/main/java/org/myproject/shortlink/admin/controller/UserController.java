package org.myproject.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.myproject.shortlink.admin.common.convention.result.Result;
import org.myproject.shortlink.admin.common.convention.result.Results;
import org.myproject.shortlink.admin.dto.response.UserRespActualDTO;
import org.myproject.shortlink.admin.dto.response.UserRespDTO;
import org.myproject.shortlink.admin.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/*
* 用户管理控制层
* */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/shortlink/v1/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username) {
        UserRespDTO result = userService.getUserByUsername(username);
        return Results.success(result);
    }

    @GetMapping("/api/shortlink/v1/user/actual/{username}")
    public Result<UserRespActualDTO> getActualUserByUsername(@PathVariable("username") String username) {
        UserRespActualDTO actualResult = BeanUtil.toBean(userService.getUserByUsername(username), UserRespActualDTO.class);
        return Results.success(actualResult);
    }
}
