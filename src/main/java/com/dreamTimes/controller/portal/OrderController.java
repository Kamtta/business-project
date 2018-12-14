package com.dreamTimes.controller.portal;

import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.User;
import com.dreamTimes.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/portal/order/")
public class OrderController {

    @Autowired
    IOrderService orderService;

    /**
     * 创建订单
     * @return
     */
    @RequestMapping(value = "create.do")
    public ServerResponse create(HttpSession session,Integer shippingId){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            return orderService.create(user.getId(),shippingId);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    /**
     * 获取购物车订单明细
     * @param session
     * @return
     */
    @RequestMapping(value = "get_order_cart_product.do")
    public ServerResponse get_order_cart_product(HttpSession session){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            return orderService.get_order_cart_product(user.getId());
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    /**
     * 订单list
     * @return
     */
    @RequestMapping(value = "list.do")
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            return orderService.list(user.getId(),pageNum,pageSize);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    /**
     * 订单详情detail
     * @return
     */
    @RequestMapping(value = "detail.do")
    public ServerResponse detail(HttpSession session,Long orderNo){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            return orderService.detail(user.getId(),orderNo);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    /**
     * 取消订单
     * @return
     */
    @RequestMapping(value = "cancel.do")
    public ServerResponse cancel(HttpSession session,Long orderNo){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            return orderService.cancel(user.getId(),orderNo);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }
}
