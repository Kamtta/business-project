package com.dreamTimes.controller.portal;


import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.User;
import com.dreamTimes.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/portal/cart/")
public class CartController {

    @Autowired
    ICartService cartService;

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
        if (o != null && o instanceof User){
            User user = (User)o;
            return cartService.add(user.getId(),productId,count);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    /**
     * 购物车list列表
     * @param session
     * @return
     */
    @RequestMapping(value = "list.do")
    public ServerResponse list(HttpSession session){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if (o != null && o instanceof User) {
            User user = (User)o;
            return cartService.list(user.getId());
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    /**
     * 更新购物车某个商品的数量
     * @param session
     * @param productId
     * @param count
     * @return
     */
    @RequestMapping(value = "update.do")
    public ServerResponse update(HttpSession session,Integer productId, Integer count){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if (o != null && o instanceof User) {
            User user = (User)o;
            return cartService.update(user.getId(),productId,count);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    /**
     * 移除购物车某个商品
     * @param session
     * @param productIds
     * @return
     */
    @RequestMapping(value = "delete_product.do")
    public ServerResponse delete_product(HttpSession session,String productIds){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if (o != null && o instanceof User) {
            User user = (User)o;
            return cartService.delete_product(user.getId(),productIds);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    /**
     * 购物车选中某个商品
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping(value = "select.do")
    public ServerResponse select(HttpSession session,Integer productId){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if (o != null && o instanceof User) {
            User user = (User)o;
            return cartService.select(user.getId(),productId);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    /**
     * 购物车取消选中某个商品
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping(value = "un_select.do")
    public ServerResponse un_select(HttpSession session,Integer productId){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if (o != null && o instanceof User) {
            User user = (User)o;
            return cartService.un_select(user.getId(),productId);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    /**
     * 查询购物车商品的数量
     * @param session
     * @return
     */
    @RequestMapping(value = "get_cart_product_count.do")
    public ServerResponse get_cart_product_count(HttpSession session){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if (o != null && o instanceof User) {
            User user = (User)o;
            return cartService.get_cart_product_count(user.getId());
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }



    /**
     * 购物车全选
     * @param session
     * @return
     */
    @RequestMapping(value = "select_all.do")
    public ServerResponse select_all(HttpSession session){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if (o != null && o instanceof User) {
            User user = (User)o;
            return cartService.select_all(user.getId());
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    /**
     * 购物车全取
     * @param session
     * @return
     */
    @RequestMapping(value = "un_select_all.do")
    public ServerResponse un_select_all(HttpSession session){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if (o != null && o instanceof User) {
            User user = (User)o;
            return cartService.un_select_all(user.getId());
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }
}
