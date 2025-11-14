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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
* 用户管理控制层
* */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /*
    * 根据用户名查询用户信息
    */
    @GetMapping("/api/shortlink/v1/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username) {
        UserRespDTO result = userService.getUserByUsername(username);
        return Results.success(result);
    }

    /*
     * 根据用户名查询无脱敏用户信息
     */
    @GetMapping("/api/shortlink/v1/user/actual/{username}")
    public Result<UserRespActualDTO> getActualUserByUsername(@PathVariable("username") String username) {
        UserRespActualDTO actualResult = BeanUtil.toBean(userService.getUserByUsername(username), UserRespActualDTO.class);
        return Results.success(actualResult);
    }

    /*
    查询用户名是否可用
     */
    @GetMapping("/api/shortlink/v1/user/has-username")
    public Result<Boolean> hasUserName(@RequestParam("username") String username) {
        return Results.success(userService.hasUserName(username));
    }
}
