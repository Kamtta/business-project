package com.dreamTimes.controller.commons.exceptionresolver;

import com.dreamTimes.commons.ResponseCode;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常
 * 处理异常，将异常进行包装，对外不暴露相关的资源
 */
@Component
public class ExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
//        打印异常的信息，便于调试
        e.printStackTrace();
        ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
        modelAndView.addObject("status",ResponseCode.EXCEPTION.getStatus());
        modelAndView.addObject("msg",ResponseCode.EXCEPTION.getMsg());
        modelAndView.addObject("data",e.getMessage());
        return modelAndView;
    }
}
