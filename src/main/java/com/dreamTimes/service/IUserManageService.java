package com.dreamTimes.service;

import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.User;

public interface IUserManageService {

    /**
     * 后台管理员登录
     * @param username
     * @param password
     * @return
     */
    ServerResponse login(String username, String password);

    /**
     * 用户列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse list( Integer pageNum, Integer pageSize);


    /**
     * 实现用户的自动登录，将token进行存储
     */
    void autoLoginToken(Integer userId,String token);


    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    User findUserByToken(String token);
}
