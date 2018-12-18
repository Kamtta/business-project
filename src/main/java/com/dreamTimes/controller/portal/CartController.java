package com.dreamTimes.controller.portal;


import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.User;
import com.dreamTimes.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
    @RequestMapping(value = "add/{productId}/{count}")
    public ServerResponse add(HttpSession session,
                              @PathVariable("productId") Integer productId,
                              @PathVariable("count") Integer count){
        User user= (User)session.getAttribute(Const.CURRENT_USER);
        return cartService.add(user.getId(),productId,count);
    }


    /**
     * 购物车list列表
     * @param session
     * @return
     */
    @RequestMapping(value = "list.do")
    public ServerResponse list(HttpSession session){
        User user= (User)session.getAttribute(Const.CURRENT_USER);
        return cartService.list(user.getId());
    }


    /**
     * 更新购物车某个商品的数量
     * @param session
     * @param productId
     * @param count
     * @return
     */
    @RequestMapping(value = "update/{productId}/{count}")
    public ServerResponse update(HttpSession session,
                                 @PathVariable("productId") Integer productId,
                                 @PathVariable("count") Integer count){
        User user= (User)session.getAttribute(Const.CURRENT_USER);
        return cartService.update(user.getId(),productId,count);
    }


    /**
     * 移除购物车某个商品
     * @param session
     * @param productIds
     * @return
     */
    @RequestMapping(value = "delete_product/{productIds}")
    public ServerResponse delete_product(HttpSession session,
                                         @PathVariable("productIds") String productIds){
        User user= (User)session.getAttribute(Const.CURRENT_USER);
        return cartService.delete_product(user.getId(),productIds);
    }


    /**
     * 购物车选中某个商品
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping(value = "select/{productId}")
    public ServerResponse select(HttpSession session,
                                 @PathVariable("productId") Integer productId){
        User user= (User)session.getAttribute(Const.CURRENT_USER);
        return cartService.select(user.getId(),productId);
    }


    /**
     * 购物车取消选中某个商品
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping(value = "un_select/{productId}")
    public ServerResponse un_select(HttpSession session,
                                    @PathVariable("productId") Integer productId){
        User user= (User)session.getAttribute(Const.CURRENT_USER);
        return cartService.un_select(user.getId(),productId);
    }


    /**
     * 查询购物车商品的数量
     * @param session
     * @return
     */
    @RequestMapping(value = "get_cart_product_count.do")
    public ServerResponse get_cart_product_count(HttpSession session){
        User user= (User)session.getAttribute(Const.CURRENT_USER);
        return cartService.get_cart_product_count(user.getId());
    }



    /**
     * 购物车全选
     * @param session
     * @return
     */
    @RequestMapping(value = "select_all.do")
    public ServerResponse select_all(HttpSession session){
        User user= (User)session.getAttribute(Const.CURRENT_USER);
        return cartService.select_all(user.getId());
    }


    /**
     * 购物车全取
     * @param session
     * @return
     */
    @RequestMapping(value = "un_select_all.do")
    public ServerResponse un_select_all(HttpSession session){
        User user= (User)session.getAttribute(Const.CURRENT_USER);
        return cartService.un_select_all(user.getId());
    }
}
