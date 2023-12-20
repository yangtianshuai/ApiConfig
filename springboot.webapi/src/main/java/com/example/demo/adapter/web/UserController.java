package com.example.demo.adapter.web;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.example.demo.client.api.IUserService;
import com.example.demo.client.dto.UserRegisterCmd;
import com.example.demo.client.dto.data.UserVO;
import com.example.demo.client.dto.query.UserListByParamQuery;
import com.example.demo.client.dto.query.UserLoginQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "user", produces="application/json")
public class UserController {

    @Autowired
    private IUserService userService;

    @Operation(summary = "测试接口")
    @GetMapping(value = "/hello")
    public String hello(Date time) {
        return "Hello, welcome to COLA world!";
    }

    @Operation(summary = "注册新用户（由管理员添加）")
    @PostMapping(value = "/register")
    public Response register(@RequestBody UserRegisterCmd cmd) {
        userService.register(cmd);
        return Response.buildSuccess();
    }


    @Operation(summary = "用户登录")
    @PostMapping(value = "/login")
    public Response login(@RequestBody UserLoginQuery userLoginQuery) {
        userService.login(userLoginQuery);
        return Response.buildSuccess();
    }

    @Operation(summary = "获取用户信息",description = "根据用户名获取用户信息")
    @GetMapping(value = "/list")
    public MultiResponse<UserVO> list(@RequestParam(required = false) String nameName) {
        return userService.listByName(UserListByParamQuery.builder().userName(nameName).build());
    }

}
