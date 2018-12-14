package com.dreamTimes.controller.manage;


import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.User;
import com.dreamTimes.service.IOrderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/order/")
public class OrderManageController {

    @Autowired
    IOrderService orderService;


    /**
     * 订单list
     * @param session
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

            return orderService.listOrders(pageNum,pageSize);

        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    /**
     * 按订单号进行查询
     * @return
     */
    @RequestMapping(value = "search.do")
    public ServerResponse search(HttpSession session,
                                 @RequestParam(value = "orderNo") Long orderNo,
                                 @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                                 @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            if(user.getRole() != Const.USER_ROLE_MANAGE){
                return ServerResponse.createServerResponseByError(ResponseCode.ROLE_ERROR.getStatus(),ResponseCode.ROLE_ERROR.getMsg());
            }
            return orderService.search(orderNo,pageNum,pageSize);

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
            if(user.getRole() != Const.USER_ROLE_MANAGE){
                return ServerResponse.createServerResponseByError(ResponseCode.ROLE_ERROR.getStatus(),ResponseCode.ROLE_ERROR.getMsg());
            }
            return orderService.orderDetail(orderNo);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    /**
     * 订单发货
     * @return
     */
    @RequestMapping(value = "send_goods.do")
    public ServerResponse send_goods(HttpSession session,Long orderNo){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            if(user.getRole() != Const.USER_ROLE_MANAGE){
                return ServerResponse.createServerResponseByError(ResponseCode.ROLE_ERROR.getStatus(),ResponseCode.ROLE_ERROR.getMsg());
            }
            return orderService.send_goods(orderNo);

        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }
}
