package com.dreamTimes.controller.commons.interceptor;

import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.User;
import com.google.gson.Gson;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

public class UserLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession httpSession = httpServletRequest.getSession();
        User user = (User)httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            httpServletResponse.reset();
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            PrintWriter printWriter = httpServletResponse.getWriter();
            ServerResponse serverResponse = ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
            Gson gson = new Gson();
            String json = gson.toJson(serverResponse);
            printWriter.write(json);
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
