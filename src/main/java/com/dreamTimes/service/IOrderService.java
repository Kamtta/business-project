package com.dreamTimes.service;

import com.dreamTimes.commons.ServerResponse;

public interface IOrderService {


    /**
     * 创建订单
     * @param userId
     * @param shippingId
     * @return
     */
    ServerResponse create(Integer userId, Integer shippingId);


    /**
     * 获取购物车订单明细
     * @param userId
     * @return
     */
    ServerResponse get_order_cart_product(Integer userId);


    /**
     * 订单list
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse list(Integer userId,Integer pageNum, Integer pageSize);


    /**
     * 订单详情detail
     * @param userId
     * @param orderNo
     * @return
     */
    ServerResponse detail(Integer userId,Long orderNo);


    /**
     * 取消订单
     * @param userId
     * @param orderNo
     * @return
     */
    ServerResponse cancel(Integer userId,Long orderNo);


    /**
     * 后台获取所有用户的订单
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse listOrders(Integer pageNum,Integer pageSize);


    /**
     * 根据订单号进行搜索
     * @param orderNo
     * @return
     */
    ServerResponse search(Long orderNo,Integer pageNum,Integer pageSize);



    /**
     * 订单详情detail
     * @param orderNo
     * @return
     */
    ServerResponse orderDetail(Long orderNo);


    /**
     * 订单发货
     * @param orderNo
     * @return
     */
    ServerResponse send_goods(Long orderNo);
}
