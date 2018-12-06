package com.dreamTimes.controller.portal;

import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.User;
import com.dreamTimes.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/portal/user/")
    public class UserController {

    @Autowired
    IUserService iUserService;

    /**
     * 登录
     * @param httpSession
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "login.do")
    public ServerResponse login(HttpSession httpSession,String username, String password){
        ServerResponse serverResponse = iUserService.login(username,password);
        if(serverResponse.isSucess()){
            httpSession.setAttribute(Const.CURRENT_USER,serverResponse.getData());
        }
        return serverResponse;
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping(value = "register.do")
    public ServerResponse register(User user){
        return iUserService.register(user);
    }

    /**
     * 判断用户名或邮箱是否有效
     */
    @RequestMapping(value = "check_valid.do")
    public ServerResponse check_valid(String str,String type){
        return iUserService.check_valid(str,type);
    }

    /**
     * 获取用户信息
     */
    @RequestMapping(value = "get_user_info.do")
    public ServerResponse get_user_info(HttpSession httpSession){
        Object object = httpSession.getAttribute(Const.CURRENT_USER);
        if(object != null && object instanceof User){
            User user = (User)object;
            User userInfo = new User();
            userInfo.setId(user.getId());
            userInfo.setUsername(user.getUsername());
            userInfo.setPhone(user.getPhone());
            userInfo.setCreateTime(user.getCreateTime());
            userInfo.setUpdateTime(user.getUpdateTime());
            userInfo.setEmail(user.getEmail());
            return ServerResponse.createServerResponseBySuccess(null,userInfo);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    /**
     * 获取当前用户的详细信息
     */
    @RequestMapping(value = "get_inforamtion.do")
    public ServerResponse get_inforamtion(HttpSession httpSession){
        Object o = httpSession.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            return ServerResponse.createServerResponseBySuccess(null,user);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }

    /**
     * 忘记密码
     * */
    @RequestMapping(value = "forget_get_question.do")
    public ServerResponse forget_get_question(String username){
        return iUserService.forget_get_question(username);
    }

    /**
     * 提交问题答案
     */
    @RequestMapping(value = "forget_check_answer.do")
    public ServerResponse forget_check_answer(String username,String question,String answer){
        return iUserService.forget_check_answer(username,question,answer);
    }

    /**
     * 忘记密码的重设密码
     * @return
     */
    @RequestMapping(value = "forget_reset_password.do")
    public ServerResponse forget_reset_password(String username,String passwordNew,String forgetToken){
        return iUserService.forget_reset_password(username,passwordNew,forgetToken);
    }
}
