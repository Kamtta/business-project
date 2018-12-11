package com.dreamTimes.controller.manage;

import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.Product;
import com.dreamTimes.pojo.User;
import com.dreamTimes.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/product/")
public class ProductManageController {

    @Autowired
    IProductService productService;
    /**
     * 新增OR更新产品
     * @param httpSession
     * @param product
     * @return
     */
    @RequestMapping(value = "save.do")
    public ServerResponse save(HttpSession httpSession, Product product){
        Object o = httpSession.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            return productService.save(user,product);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }

    /**
     * 产品上下架
     * @param session
     * @param productId
     * @param status
     * @return
     */
    @RequestMapping(value = "set_sale_status.do")
    public ServerResponse set_sale_status(HttpSession session,Integer productId,Integer status){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            return productService.set_sale_status(user,productId,status);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    /**
     * 查看商品的详情
     * @param httpSession
     * @param productId
     * @return
     */
    @RequestMapping(value = "detail.do")
    public ServerResponse detail(HttpSession httpSession,Integer productId){
        Object o = httpSession.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            return productService.detail(user,productId);
        }
        return  ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    @RequestMapping(value = "list.do")
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "pageNum" ,required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            return productService.list(user,pageNum,pageSize);
        }
        return  ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    @RequestMapping(value = "search.do")
    public ServerResponse search(HttpSession session,
                               @RequestParam(value = "productId" ,required = false)Integer productId,
                               @RequestParam(value = "productName",required = false)String productName,
                               @RequestParam(value = "pageNum" ,required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            return productService.search(user,productId,productName,pageNum,pageSize);
        }
        return  ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }



}
