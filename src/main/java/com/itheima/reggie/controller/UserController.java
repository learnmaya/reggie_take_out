package com.itheima.reggie.controller;


import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;


@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "User-related API")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    @ApiOperation("Send verification email API")
    public Result<String> sendMsg(@RequestBody User user, HttpSession session) throws MessagingException {
        return userService.sendMsg(user,session);
    }

    @PostMapping("/login")
    @ApiOperation("User Login API")
    @ApiImplicitParam(name = "map",value = "Map collection receives data",required = true)
    public Result<User> login(@RequestBody Map map, HttpSession session) {
        return userService.userLogin(map,session);
    }

    @PostMapping("/loginout")
    @ApiOperation("User logout API")
    public Result<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return Result.success("Exit successfully");
    }

}
