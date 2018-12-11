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
}
