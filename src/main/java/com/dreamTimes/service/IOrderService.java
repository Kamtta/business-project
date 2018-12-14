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
}
