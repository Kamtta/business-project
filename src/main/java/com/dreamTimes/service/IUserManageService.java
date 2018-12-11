package com.dreamTimes.service;

import com.dreamTimes.commons.ServerResponse;

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
}
