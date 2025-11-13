package org.myproject.shortlink.admin.controller;

import lombok.RequiredArgsConstructor;
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
    public UserRespDTO getUserByUsername(@PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }
}
