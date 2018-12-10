package com.dreamTimes.service.impl;

import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.dao.UserMapper;
import com.dreamTimes.pojo.User;
import com.dreamTimes.service.IUserManageService;
import com.dreamTimes.utils.MD5Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManageImpl implements IUserManageService {

    @Autowired
    UserMapper userMapper;
    @Override
    public ServerResponse login(String username, String password) {
//        step1:非空校验
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        step2:判断用户是否存在
        int result = userMapper.checkUsername(username);
        if(result <= 0){
            return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_EXITS.getStatus(),ResponseCode.USER_NOT_EXITS.getMsg());
        }

//        step3：判断用户密码是否正确
        User user = userMapper.selectUserByUsernameAndPassword(username,MD5Utils.getMD5Code(password));
        if(user == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PASSWORD_ERROR.getStatus(),ResponseCode.PASSWORD_ERROR.getMsg());
        }
//        step4:判断用户的权限
        if(user.getRole() != Const.USER_ROLE_MANAGE){
            return ServerResponse.createServerResponseByError(ResponseCode.ROLE_ERROR.getStatus(),ResponseCode.ROLE_ERROR.getMsg());
        }
//        step5:返回结果
        user.setPassword("");
        return ServerResponse.createServerResponseBySuccess(null,user);
    }
}
