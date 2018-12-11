package com.dreamTimes.controller.manage;

import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.User;
import com.dreamTimes.service.IUserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/user/")
public class UserManageController {

    @Autowired
    IUserManageService userManageService;

    /**
     * 后台管理员登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "login.do")
    public ServerResponse login(HttpSession session,String username, String password){
        ServerResponse serverResponse = userManageService.login(username,password);
        if(serverResponse.isSucess()){
            session.setAttribute(Const.CURRENT_USER,serverResponse.getData());
        }
        return serverResponse;
    }


    /**
     * 用户列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list.do")
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            if(user.getRole() != Const.USER_ROLE_MANAGE){
                return ServerResponse.createServerResponseByError(ResponseCode.ROLE_ERROR.getStatus(),ResponseCode.ROLE_ERROR.getMsg());
            }
            return userManageService.list(pageNum,pageSize);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }
}
