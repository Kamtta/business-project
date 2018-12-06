package com.dreamTimes.service;

import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.User;

public interface IUserService {
    /**
     * 用户业务逻辑
     */


    /**
     * 登录
     */
    ServerResponse login(String username, String password);


    /**
     * 用户注册
     * @param user
     * @return
     */
    ServerResponse register(User user);


    /**
     * 判断用户名或者邮箱是否存在
     * @param str
     * @param type
     * @return
     */
    ServerResponse check_valid(String str,String type);


    /**
     * 根据用户名查询密保问题
     * @param username
     * @return
     */
    ServerResponse forget_get_question(String username);


    /**
     * 判断密保问题是否正确并返回一个token
     * @param username
     * @param question
     * @param answer
     * @return
     */
    ServerResponse forget_check_answer(String username,String question,String answer);


    /**
     * 根据token来避免横向越权，重设密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    ServerResponse forget_reset_password(String username,String passwordNew,String forgetToken);
}
