package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2018/11/23.
 */
public interface IUserSerive {



    ServerResponse<User> login(@Param(value = "username") String username, @Param(value = "password") String password);


    ServerResponse<String> register(User user);
}
