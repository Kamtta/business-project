package com.dreamTimes.service;

import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.pojo.Shipping;

public interface IShippingService {

    /**
     * 添加地址
     * @param shipping
     * @return
     */
    ServerResponse add(Shipping shipping);


    /**
     * 删除地址
     * @param shippingId
     * @param userId
     * @return
     */
    ServerResponse del(Integer userId,Integer shippingId);


    /**
     * 登录状态下更新地址
     * @param shipping
     * @return
     */
    ServerResponse update(Shipping shipping);


    /**
     * 选中查看具体地址
     * @param userId
     * @param shippingId
     * @return
     */
    ServerResponse select(Integer userId,Integer shippingId);


    /**
     * 地址列表
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse list(Integer userId, Integer pageNum, Integer pageSize);
}
