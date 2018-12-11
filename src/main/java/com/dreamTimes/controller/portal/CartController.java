package com.dreamTimes.controller.portal;


import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/portal/cart/")
public class CartController {


    /**
     * 购物车添加商品
     * @param session
     * @param productId
     * @param count
     * @return
     */
    @RequestMapping(value = "add.do")
    public ServerResponse add(HttpSession session,Integer productId, Integer count){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if (o != null){

        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }
}
