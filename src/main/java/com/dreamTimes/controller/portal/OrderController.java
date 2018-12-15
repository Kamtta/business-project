package com.dreamTimes.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.User;
import com.dreamTimes.service.IOrderService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

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


    /**
     * 支付
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "pay.do")
    public ServerResponse pay(HttpSession session,Long orderNo){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            User user = (User)o;
            return orderService.pay(user.getId(),orderNo);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }


    /**
     * 支付宝回调
     * @return
     */
    @RequestMapping(value = "alipay_callback.do")
    public String alipay_callback(HttpServletRequest httpServletRequest){
//        参数数组变成字符串
        Map<String,String[]> paramMap = httpServletRequest.getParameterMap();
        Map<String,String> params = Maps.newHashMap();
        if(paramMap != null && paramMap.size() > 0){
            Iterator<String> keySet = paramMap.keySet().iterator();
            while (keySet.hasNext()){
                String key = keySet.next();
                String[] temp = paramMap.get(key);
                String str = "";
                for(int i = 0; i < temp.length;i++){
                    str = (i == temp.length-1) ? str+temp[i] : str + temp[i] + ",";
                }
                params.put(key,str);
            }
        }

//                支付宝验签
        try {
            params.remove("sign_type");//去除签名类型
            boolean result = AlipaySignature.rsaCheckV2(params,Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
            if(!result){
                return Const.FAIL;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return orderService.alipay_callback(params);
    }


    /**
     * 查看订单的支付状态
     * @return
     */
    @RequestMapping(value = "query_order_pay_status.do")
   public ServerResponse query_order_pay_status(HttpSession session,Long orderNo){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if(o != null && o instanceof User){
            return orderService.query_order_pay_status(orderNo);
        }
        return ServerResponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
   }
}
