package com.dreamTimes.controller.portal;

import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.Shipping;
import com.dreamTimes.pojo.User;
import com.dreamTimes.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ServerResponse add(HttpSession session,Shipping shipping){
        User user= (User)session.getAttribute(Const.CURRENT_USER);
        shipping.setUserId(user.getId());
        return shippingService.add(shipping);
    }


    /**
     * 删除地址
     * @return
     */
    @RequestMapping(value = "del/{shippingId}")
    public ServerResponse del(HttpSession session,
                              @PathVariable("shippingId") Integer shippingId){
        User user= (User)session.getAttribute(Const.CURRENT_USER);
        return shippingService.del(user.getId(),shippingId);
    }


    /**
     * 登录状态更新地址
     * @return
     */
    @RequestMapping(value = "update.do")
    public ServerResponse update(HttpSession session,Shipping shipping){
        User user= (User)session.getAttribute(Const.CURRENT_USER);
        shipping.setUserId(user.getId());
        return shippingService.update(shipping);
    }


    /**
     * 选中查看具体的地址
     * @return
     */
    @RequestMapping(value = "select/{shippingId}")
    public ServerResponse select(HttpSession session,
                                 @PathVariable("shippingId") Integer shippingId){
        User user= (User)session.getAttribute(Const.CURRENT_USER);
        return shippingService.select(user.getId(),shippingId);
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
        User user= (User)session.getAttribute(Const.CURRENT_USER);
        return shippingService.list(user.getId(),pageNum,pageSize);
    }
}
