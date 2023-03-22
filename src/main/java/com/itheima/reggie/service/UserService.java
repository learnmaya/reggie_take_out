package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.User;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;


public interface UserService extends IService<User> {

    Result<User> userLogin(Map map, HttpSession session);

    Result<String> sendMsg(User user, HttpSession session) throws MessagingException;


}
