package com.dreamTimes.controller.commons.interceptor;

import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.User;
import com.dreamTimes.service.IUserManageService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * 权限拦截器
 */
public class AuthorityInterceptor implements HandlerInterceptor {

    @Autowired
    IUserManageService userManageService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            Cookie[] cookies = httpServletRequest.getCookies();
            if(cookies != null && cookies.length > 0){
                for (Cookie cookie:
                        cookies) {
                    String cookieName = cookie.getName();
                    if(cookieName.equals(Const.AUTO_LOGIN_TOKEN)){
                        String token = cookie.getValue();
                        user = userManageService.findUserByToken(token);
                        session.setAttribute(Const.CURRENT_USER,user);
                        break;
                    }
                }
            }
        }

        if(user == null || user.getRole() != Const.USER_ROLE_MANAGE){
//            刷新一下缓冲区的数据
            httpServletResponse.reset();
            httpServletResponse.setCharacterEncoding("utf-8");
//            设置应用返回的类型，跟spring上设置的格式一样
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            PrintWriter printWriter = httpServletResponse.getWriter();
            if(user == null){
                ServerResponse serverResponse = ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
                Gson gson = new Gson();
                String json = gson.toJson(serverResponse);
                printWriter.write(json);
            }
            if(user.getRole() != Const.USER_ROLE_MANAGE){
                ServerResponse serverResponse = ServerResponse.createServerResponseByError(ResponseCode.ROLE_ERROR.getStatus(),ResponseCode.ROLE_ERROR.getMsg());
                Gson gson = new Gson();
                String json = gson.toJson(serverResponse);
                printWriter.write(json);
            }
            printWriter.flush();
            printWriter.close();
            return false;

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
