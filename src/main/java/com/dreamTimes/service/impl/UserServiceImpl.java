package com.dreamTimes.service.impl;

import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.dao.UserMapper;
import com.dreamTimes.pojo.User;
import com.dreamTimes.service.IUserService;
import com.dreamTimes.utils.MD5Utils;
import com.dreamTimes.utils.RedisPoolUtils;
import com.dreamTimes.utils.TokenCache;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public ServerResponse login(String username, String password) {
//        step1:非空校验
        password = MD5Utils.getMD5Code(password);
        if(StringUtils.isBlank(username)){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        step2:判断用户名是否存在
        /*int i = userMapper.checkUsername(username);
        if(i <= 0){
            return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_EXITS.getStatus(),ResponseCode.USER_NOT_EXITS.getMsg());
        }*/
        ServerResponse serverResponse = check_valid(username,Const.USERNAME);
        if (serverResponse.isSucess()){
            return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_EXITS.getStatus(),ResponseCode.USER_NOT_EXITS.getMsg());
        }
//        step3:判断用户密码是否正确
        User user = userMapper.selectUserByUsernameAndPassword(username,password);
        if(user == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PASSWORD_ERROR.getStatus(),ResponseCode.PASSWORD_ERROR.getMsg());
        }
//        step4:返回接口数据
        user.setPassword("");
        return ServerResponse.createServerResponseBySuccess(null,user);
    }

    @Override
    public ServerResponse register(User user) {
//        step1:非空校验
        if(user == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        step2:判断用户名是否已存在
       /* int i = userMapper.checkUsername(user.getUsername());
        if(i > 0){
            return ServerResponse.createServerResponseByError(ResponseCode.USER_EXITS.getStatus(),ResponseCode.USER_EXITS.getMsg());
        }*/
       ServerResponse serverResponse = check_valid(user.getUsername(),Const.USERNAME);
       if (!serverResponse.isSucess()){
           return serverResponse;
       }
//        step3:判断邮箱是否已存在
        /*int result = userMapper.checkEmail(user.getEmail());
        if(result > 0){
            return ServerResponse.createServerResponseByError(ResponseCode.EMAIL_EXITS.getStatus(),ResponseCode.EMAIL_EXITS.getMsg());
        }*/
        ServerResponse email_Response = check_valid(user.getEmail(),Const.EMAIL);
        if (!email_Response.isSucess()){
            return email_Response;
        }
//        step4:用户注册
        user.setPassword(MD5Utils.getMD5Code(user.getPassword()));
       int count = userMapper.insert(user);
        if(count > 0){
            return ServerResponse.createServerResponseBySuccess(Const.REGISTER_SUCCESS);
        }
//        step5:返回结果
        return ServerResponse.createServerResponseByError(ResponseCode.REGISTER_FAIL.getStatus(),ResponseCode.REGISTER_FAIL.getMsg());
    }

    @Override
    public ServerResponse check_valid(String str, String type) {
//        step1:非空校验
        if(StringUtils.isBlank(str)||StringUtils.isBlank(type)){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        step2:判断用户名和邮箱是否存在
        if(type.equals(Const.USERNAME)){
            int username_result = userMapper.checkUsername(str);
            if(username_result > 0){
                return ServerResponse.createServerResponseByError(ResponseCode.USER_EXITS.getStatus(),ResponseCode.USER_EXITS.getMsg());
            }
            return ServerResponse.createServerResponseBySuccess(Const.CHECK_SUCCESS);
        }else if(type.equals(Const.EMAIL)){
            int email_result = userMapper.checkEmail(str);
            if(email_result > 0){
                return ServerResponse.createServerResponseByError(ResponseCode.EMAIL_EXITS.getStatus(),ResponseCode.EMAIL_EXITS.getMsg());
            }
            return ServerResponse.createServerResponseBySuccess(Const.CHECK_SUCCESS);
        }
//        step3:返回结果
        return ServerResponse.createServerResponseByError(ResponseCode.TYPE_ERROR.getStatus(),ResponseCode.TYPE_ERROR.getMsg());
    }

    @Override
    public ServerResponse forget_get_question(String username) {
//        step1:非空校验
        if(StringUtils.isBlank(username)){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        step2:判断用户名是否存在
        ServerResponse serverResponse = check_valid(username,Const.USERNAME);
        if (serverResponse.isSucess()){
            return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_EXITS.getStatus(),ResponseCode.USER_NOT_EXITS.getMsg());
        }
//        step3:判断密保问题是否为空
        String question = userMapper.forget_get_question(username);
        if(StringUtils.isBlank(question)){
            return ServerResponse.createServerResponseByError(ResponseCode.QUESTION_ERROR.getStatus(),ResponseCode.QUESTION_ERROR.getMsg());
        }
//        step4:返回结果
        return ServerResponse.createServerResponseBySuccess(null,question);
    }

    @Override
    public ServerResponse forget_check_answer(String username, String question, String answer) {
//        step1:非空校验
        if(StringUtils.isBlank(username) || StringUtils.isBlank(question) || StringUtils.isBlank(answer)){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        step2:判断答案是否正确
        int answer_result = userMapper.forget_check_answer(username,question,answer);
        if (answer_result <= 0){
            return ServerResponse.createServerResponseByError(ResponseCode.ANSWER_ERROR.getStatus(),ResponseCode.ANSWER_ERROR.getMsg());
        }
//        step3:返回结果
//        生成UUID唯一标识
        String token = UUID.randomUUID().toString();
//        将UUID放进guava缓冲区
//        TokenCache.set(username,token);
//        使用Redis内存数据库代替guava缓存
        RedisPoolUtils.set(username,token);
        return ServerResponse.createServerResponseBySuccess(null,token);
    }

    @Override
    public ServerResponse forget_reset_password(String username, String passwordNew, String forgetToken) {
//        step1:非空校验
        if(StringUtils.isBlank(username) || StringUtils.isBlank(passwordNew) || StringUtils.isBlank(forgetToken)){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        step2:判断用户是否存在
        ServerResponse serverResponse = check_valid(username,Const.USERNAME);
        if (serverResponse.isSucess()){
            return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_EXITS.getStatus(),ResponseCode.USER_NOT_EXITS.getMsg());
        }
//        step3:使用forgetToken校验是否存在横向越权的情况
//        String token = TokenCache.get(username);
//        使用Redis技术代替guava缓存
        String token = RedisPoolUtils.get(username);
        if (StringUtils.isBlank(token)){
            return ServerResponse.createServerResponseByError(ResponseCode.TOKEN_OUT_DATE.getStatus(),ResponseCode.TOKEN_OUT_DATE.getMsg());
        }
        if(!token.equals(forgetToken)){
            return ServerResponse.createServerResponseByError(ResponseCode.TOKEN_FAIL.getStatus(),ResponseCode.TOKEN_FAIL.getMsg());
        }
//        step4:重设密码
        int alter_result = userMapper.forget_reset_password(username,MD5Utils.getMD5Code(passwordNew));
        if(alter_result <= 0){
            return ServerResponse.createServerResponseByError(ResponseCode.ALTER_PASSWORD_FAIL.getStatus(),ResponseCode.ALTER_PASSWORD_FAIL.getMsg());
        }
//        step5:返回结果
        return ServerResponse.createServerResponseBySuccess(Const.ALTER_SUCCESS);
    }

    @Override
    public ServerResponse reset_password(String username, String passwordOld, String passwordNew) {

//        step1:非空检验
        if(StringUtils.isBlank(passwordOld) || StringUtils.isBlank(passwordNew)){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        step2:判断旧密码是否正确
        User user = userMapper.selectUserByUsernameAndPassword(username,MD5Utils.getMD5Code(passwordOld));
        if(user == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PASSWORDOLD_ERROR.getStatus(),ResponseCode.PASSWORDOLD_ERROR.getMsg());
        }
//        step3:修改密码
        user.setPassword(MD5Utils.getMD5Code(passwordNew));
        int alter_result = userMapper.updateByPrimaryKey(user);
        if(alter_result <= 0){
            return ServerResponse.createServerResponseByError(ResponseCode.ALTER_PASSWORD_FAIL.getStatus(),ResponseCode.ALTER_PASSWORD_FAIL.getMsg());
        }
//        step4:返回结果
        return ServerResponse.createServerResponseBySuccess(Const.ALTER_SUCCESS);
    }

    @Override
    public ServerResponse update_information(User user) {
//        step1:非空检验
        if(user == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        step2:更新信息
        int update_result = userMapper.update_information(user);
        if(update_result <= 0){
            return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
        }
//        step3:返回结果
        return ServerResponse.createServerResponseBySuccess(Const.UPDATE_SUCCESS);
    }
}
