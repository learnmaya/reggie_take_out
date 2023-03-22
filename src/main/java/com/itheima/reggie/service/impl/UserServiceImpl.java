package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.mapper.UserMapper;
import com.itheima.reggie.service.UserService;
import com.itheima.reggie.utils.MailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public Result<User> userLogin(Map map, HttpSession session) {
        log.info(map.toString());
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();

        String codeInSession = session.getAttribute(phone).toString();
        log.info("The code you entered {}, the code in session {}, is calculated as {}", code, codeInSession, (code != null && code.equals(codeInSession)));
        if (code != null && code.equals(codeInSession)) {

            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);
            User user = this.getOne(queryWrapper);

            if (user == null) {
                user = new User();
                user.setPhone(phone);
                user.setName("user" + codeInSession);
                this.save(user);
            }
            session.setAttribute("user",user.getId());
            return Result.success(user);
        }
        return Result.error("Login failed");
    }

    @Override
    public Result<String> sendMsg(User user, HttpSession session) throws MessagingException {
        String email = user.getPhone();
        if (!email.isEmpty()) {
            String code = MailUtils.achieveCode();
            log.info(code);
            MailUtils.sendTestMail(email, code);
            session.setAttribute(email, code);
            return Result.success("Verification code sent successfully");
        }
        return Result.error("Verification code send failed");
    }

}
