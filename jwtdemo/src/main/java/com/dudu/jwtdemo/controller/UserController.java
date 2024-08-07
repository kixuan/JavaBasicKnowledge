package com.dudu.jwtdemo.controller;

import com.dudu.jwtdemo.api.CommonResult;
import com.dudu.jwtdemo.mbg.model.User;
import com.dudu.jwtdemo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(tags = "UserController", description = "用户管理")
public class UserController {
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Resource
    private UserService userService;

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public CommonResult login(User user) {
        String token = userService.login(user);
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);// tokenHead: 'Bearer '  #JWT负载中拿到开头
        tokenMap.put("state", true);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "根据id获取用户信息")
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public CommonResult getInfoAll(int id) {
        User user = userService.getInfoById(id);
        if (user == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        return CommonResult.success(user.toString());
    }
}
