package com.dreamTimes.controller.portal;

import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.User;
import com.dreamTimes.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
    @RequestMapping(value = "login/{username}/{password}")
    public ServerResponse login(HttpSession httpSession,
                                @PathVariable("username") String username,
                                @PathVariable("password")String password){
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
    @RequestMapping(value = "check_valid/{str}/{type}")
    public ServerResponse check_valid(@PathVariable("str") String str,
                                      @PathVariable("type") String type){
        return iUserService.check_valid(str,type);
    }

    /**
     * 获取用户信息
     */
    @RequestMapping(value = "get_user_info.do")
    public ServerResponse get_user_info(HttpSession httpSession){
        User user= (User)httpSession.getAttribute(Const.CURRENT_USER);
        User userInfo = new User();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setPhone(user.getPhone());
        userInfo.setCreateTime(user.getCreateTime());
        userInfo.setUpdateTime(user.getUpdateTime());
        userInfo.setEmail(user.getEmail());
        return ServerResponse.createServerResponseBySuccess(null,userInfo);
    }


    /**
     * 获取当前用户的详细信息
     */
    @RequestMapping(value = "get_inforamtion.do")
    public ServerResponse get_inforamtion(HttpSession httpSession){
        User user= (User)httpSession.getAttribute(Const.CURRENT_USER);
        return ServerResponse.createServerResponseBySuccess(null,user);
    }

    /**
     * 忘记密码，获取密保问题
     * */
    @RequestMapping(value = "forget_get_question/{username}")
    public ServerResponse forget_get_question(@PathVariable("username") String username){
        return iUserService.forget_get_question(username);
    }

    /**
     * 提交问题答案
     */
    @RequestMapping(value = "forget_check_answer/{username}/{question}/{answer}")
    public ServerResponse forget_check_answer(@PathVariable("username") String username,
                                              @PathVariable("question") String question,
                                              @PathVariable("answer") String answer){
        return iUserService.forget_check_answer(username,question,answer);
    }

    /**
     * 忘记密码的重设密码
     * @return
     */
    @RequestMapping(value = "forget_reset_password/{username}/{passwordNew}/{forgetToken}")
    public ServerResponse forget_reset_password(@PathVariable("username") String username,
                                                @PathVariable("passwordNew") String passwordNew,
                                                @PathVariable("forgetToken") String forgetToken){
        return iUserService.forget_reset_password(username,passwordNew,forgetToken);
    }


    /**
     * 登录状态中重置密码
     * @param httpSession
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @RequestMapping(value = "reset_password/{passwordOld}/{passwordNew}")
    public ServerResponse reset_password(HttpSession httpSession,
                                         @PathVariable("passwordOld") String passwordOld,
                                         @PathVariable("passwordNew") String passwordNew){
        User user= (User)httpSession.getAttribute(Const.CURRENT_USER);
        String username = user.getUsername();
        return iUserService.reset_password(username,passwordOld,passwordNew);
    }

    /**
     * 登录状态下更新个人信息
     * @param httpSession
     * @param user
     * @return
     */
    @RequestMapping(value = "update_information.do")
    public ServerResponse update_information(HttpSession httpSession,User user){
        User u= (User)httpSession.getAttribute(Const.CURRENT_USER);
        user.setId(u.getId());
        return iUserService.update_information(user);
    }


    /**
     * 退出登录
     * @return
     */
    @RequestMapping(value = "logout.do")
    public ServerResponse logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        if (session.getAttribute(Const.CURRENT_USER) == null){
            return ServerResponse.createServerResponseBySuccess(Const.LOGOUT_SUCCESS);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.SERVER_ERROR.getStatus(),ResponseCode.SERVER_ERROR.getMsg());
    }
}
