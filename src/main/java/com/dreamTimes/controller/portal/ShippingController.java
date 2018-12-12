package com.dreamTimes.controller.portal;

import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.Shipping;
import com.dreamTimes.pojo.User;
import com.dreamTimes.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/portal/shipping/")
public class ShippingController {

    @Autowired
    IShippingService shippingService;


    /**
     * 添加地址
     * @param session
     * @param shipping
     * @return
     */
    @RequestMapping(value = "add.do")
    public ServerResponse add(HttpSession session, Shipping shipping){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            shipping.setUserId(user.getId());
            return shippingService.add(shipping);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    /**
     * 删除地址
     * @return
     */
    @RequestMapping(value = "del.do")
    public ServerResponse del(HttpSession session,Integer shippingId){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            return shippingService.del(user.getId(),shippingId);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    /**
     * 登录状态更新地址
     * @return
     */
    @RequestMapping(value = "update.do")
    public ServerResponse update(HttpSession session,Shipping shipping){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            shipping.setUserId(user.getId());
            return shippingService.update(shipping);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    /**
     * 选中查看具体的地址
     * @return
     */
    @RequestMapping(value = "select.do")
    public ServerResponse select(HttpSession session,Integer shippingId){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            return shippingService.select(user.getId(),shippingId);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    /**
     * 地址列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list")
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            return shippingService.list(user.getId(),pageNum,pageSize);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }
}
