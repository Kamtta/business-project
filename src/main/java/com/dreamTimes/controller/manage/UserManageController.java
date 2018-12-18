package com.dreamTimes.controller.manage;

import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.User;
import com.dreamTimes.service.IUserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
    @RequestMapping(value = "login/{username}/{password}")
    public ServerResponse login(HttpSession session,
                                @PathVariable("username") String username,
                                @PathVariable("password") String password){
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
    public ServerResponse list(@RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
            return userManageService.list(pageNum,pageSize);
    }
}
