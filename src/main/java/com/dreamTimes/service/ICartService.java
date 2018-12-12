package com.dreamTimes.service;

import com.dreamTimes.commons.ServerResponse;

public interface ICartService {


    /**
     * 购物车添加商品
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    ServerResponse add(Integer userId,Integer productId, Integer count);


    /**
     * 购物车list列表
     * @param userId
     * @return
     */
    ServerResponse list(Integer userId);


    /**
     * 更新购物车某个商品的数量
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    ServerResponse update(Integer userId,Integer productId, Integer count);


    /**
     * 移除购物车某个商品
     * @param userId
     * @param productIds
     * @return
     */
    ServerResponse delete_product(Integer userId,String productIds);


    /**
     * 购物车选中某个商品
     * @param userId
     * @param productId
     * @return
     */
    ServerResponse select(Integer userId,Integer productId);


    /**
     * 购物车取消选中某个商品
     * @param userId
     * @param productId
     * @return
     */
    ServerResponse un_select(Integer userId,Integer productId);


    /**
     * 查询购物车商品的数量
     * @param userId
     * @return
     */
    ServerResponse get_cart_product_count(Integer userId);



    /**
     * 购物车全选
     * @param userId
     * @return
     */
    ServerResponse select_all(Integer userId);


    /**
     * 购物车全取
     * @param userId
     * @return
     */
    ServerResponse un_select_all(Integer userId);
}
