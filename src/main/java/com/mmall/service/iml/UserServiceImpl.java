package com.mmall.service.iml;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserSerive;
import com.mmall.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/11/23.
 */
@Service("userService")
public class UserServiceImpl implements IUserSerive {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0)
        {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        //模拟MD5
        User user=userMapper.toLogin(username, MD5Util.MD5EncodeUtf8(password));
        if(user == null){
            return ServerResponse.createByErrorMessage("密码不存在");
        }
        return ServerResponse.createBySuccess(user);
    }


    @Override
    public ServerResponse<String> register(User user) {
        ServerResponse vaildResponse=checkValid(user.getUsername(),Const.USERNAME);
        if(!vaildResponse.isSuccess()){
            return vaildResponse;
        }
        vaildResponse=checkValid(user.getEmail(),Const.EMIAL);
        if (!vaildResponse.isSuccess()){
            return vaildResponse;
        }
        String password=MD5Util.MD5EncodeUtf8(user.getPassword());
        user.setPassword(password);
        if(userMapper.insert(user)>0){
            return ServerResponse.createBySuccessMessage("注册成功");
        }
        return ServerResponse.createBySuccessMessage("注册失败");

    }


    public ServerResponse<String> checkValid(String str,String type){
        if(Const.USERNAME.equals(type)){
            int result = userMapper.checkUsername(str);
            if(result > 0 ){
                return ServerResponse.createByErrorMessage("用户名已存在");
            }
        }
        if(Const.EMIAL.equals(type)){
            int result= userMapper.checkByEmail(str);
            if(result > 0 ){
                return ServerResponse.createByErrorMessage("邮箱已存在");
            }
        }
        return ServerResponse.createBySuccess();
    }
}
