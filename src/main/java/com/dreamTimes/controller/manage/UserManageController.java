package com.dreamTimes.controller.manage;

import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.User;
import com.dreamTimes.service.IUserManageService;
import com.dreamTimes.utils.IpUtils;
import com.dreamTimes.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.SocketException;
import java.net.UnknownHostException;

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
                                HttpServletRequest request,
                                HttpServletResponse response,
                                @PathVariable("username") String username,
                                @PathVariable("password") String password){
        ServerResponse serverResponse = userManageService.login(username,password);
        if(serverResponse.isSucess()){
//            使用Mac地址实现单点登录的功能，将Mac地址进行加密
            try {
                String ip = IpUtils.getRemoteAddress(request);
                String mac = IpUtils.getMACAddress(ip);
                String token = MD5Utils.getMD5Code(mac);
                Cookie cookie = new Cookie(Const.AUTO_LOGIN_TOKEN,token);
                cookie.setMaxAge(60*60*24*7);//设置cookie的生命周期为7天
                cookie.setPath("/");//将cookie的路径设置种种在根目录，不然会找不到相应的cookie
                response.addCookie(cookie);
//                将token进行永久性的存储
                User user = (User)serverResponse.getData();
                userManageService.autoLoginToken(user.getId(),token);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (SocketException e) {
                e.printStackTrace();
            }

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
