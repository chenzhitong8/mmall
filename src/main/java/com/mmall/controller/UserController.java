package com.mmall.controller;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserSerive;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2018/11/23.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserSerive userSerive;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password, HttpSession session) {

        ServerResponse<User> response = userSerive.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;

    }

    @RequestMapping(value="register.do", method = RequestMethod.POST)
    public ServerResponse<String> regist(User user) {
        return userSerive.register(user);
    }


    @RequestMapping(value = "index.do",method = RequestMethod.GET)
    public String index(){

        return "index";
    }

}
