package org.myproject.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.myproject.shortlink.admin.common.convention.result.Result;
import org.myproject.shortlink.admin.common.convention.result.Results;
import org.myproject.shortlink.admin.dto.request.UserLoginReqDTO;
import org.myproject.shortlink.admin.dto.request.UserRegisterReqDTO;
import org.myproject.shortlink.admin.dto.request.UserUpdateReqDTO;
import org.myproject.shortlink.admin.dto.response.UserLoginRespDTO;
import org.myproject.shortlink.admin.dto.response.UserRespActualDTO;
import org.myproject.shortlink.admin.dto.response.UserRespDTO;
import org.myproject.shortlink.admin.service.UserService;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/api/short-link/v1/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username) {
        UserRespDTO result = userService.getUserByUsername(username);
        return Results.success(result);
    }

    /*
     * 根据用户名查询无脱敏用户信息
     */
    @GetMapping("/api/short-link/v1/user/actual/{username}")
    public Result<UserRespActualDTO> getActualUserByUsername(@PathVariable("username") String username) {
        UserRespActualDTO actualResult = BeanUtil.toBean(userService.getUserByUsername(username), UserRespActualDTO.class);
        return Results.success(actualResult);
    }

    /*
    查询用户名是否可用
     */
    @GetMapping("/api/short-link/v1/user/has-username")
    public Result<Boolean> hasUserName(@RequestParam("username") String username) {
        return Results.success(userService.hasUserName(username));
    }

    /*
    注册用户
     */
    @PostMapping("/api/short-link/v1/user")
    public Result<Void> register(@RequestBody UserRegisterReqDTO requestParam) {
        userService.register(requestParam);
        return Results.success();
    }

    /*
    根据用户名更新用户信息
     */
    @PutMapping("/api/short-link/v1/user")
    public Result<Void> update(@RequestBody UserUpdateReqDTO requestParam) {
        userService.update(requestParam);
        return Results.success();
    }

    /*
    用户登录
     */
    @PostMapping("/api/short-link/v1/user/login")
    public Result<UserLoginRespDTO> login(@RequestBody UserLoginReqDTO requestParam) {
        UserLoginRespDTO result = userService.login(requestParam);
        return Results.success(result);
    }

    /*
    验证用户登录
     */
    @GetMapping("/api/short-link/v1/user/check-login")
    public Result<Boolean> checklogin(@RequestParam("username") String username, @RequestParam("token") String token) {
        Boolean result = userService.checklogin(username, token);
        return Results.success(result);
    }

    /*
    用户退出登录
     */
    @DeleteMapping("/api/short-link/v1/user/logout")
    public Result<Void> logout(@RequestParam("username") String username, @RequestParam("token") String token) {
        userService.logout(username, token);
        return Results.success();
    }
}
