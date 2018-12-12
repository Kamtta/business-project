package com.dreamTimes.service.impl;

import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.dao.ShippingMapper;
import com.dreamTimes.pojo.Shipping;
import com.dreamTimes.service.IShippingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    ShippingMapper shippingMapper;

    @Override
    public ServerResponse add(Shipping shipping) {
//        非空校验
        if(shipping == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        添加商品
        int insert_result = shippingMapper.insert(shipping);
        if(insert_result <= 0){
            return ServerResponse.createServerResponseByError(ResponseCode.INSERT_ADDRESS_FAIL.getStatus(),ResponseCode.INSERT_ADDRESS_FAIL.getMsg());
        }
//        返回结果
        Map<String,Integer> map = Maps.newHashMap();
        map.put("shippingId",shipping.getId());

        return ServerResponse.createServerResponseBySuccess(Const.INSERT_ADDRESS_SUCCESS,map);
    }

    @Override
    public ServerResponse del(Integer userId,Integer shippingId) {
//        非空判断
        if(shippingId == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        删除地址
        Shipping shipping = shippingMapper.selectByPrimaryKey(shippingId);
        if(shipping != null && shipping.getUserId() == userId){
            int delete_result = shippingMapper.deleteByPrimaryKey(shippingId);
            if(delete_result <= 0){
                return ServerResponse.createServerResponseByError(ResponseCode.DELETE_ADDRESS_FAIL.getStatus(),ResponseCode.DELETE_ADDRESS_FAIL.getMsg());
            }
            return ServerResponse.createServerResponseBySuccess(Const.DELETE_ADDRESS_SUCCESS);
        }
//        返回结果
        return ServerResponse.createServerResponseByError(ResponseCode.DELETE_ADDRESS_FAIL.getStatus(),ResponseCode.DELETE_ADDRESS_FAIL.getMsg());
    }

    @Override
    public ServerResponse update(Shipping shipping) {
//        非空校验
        if(shipping == null || shipping.getId() == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        更新地址
        Shipping shipping1 = shippingMapper.selectByPrimaryKey(shipping.getId());
        if(shipping1 != null && shipping1.getUserId() == shipping.getUserId()){
            int update_result = shippingMapper.updateByPrimaryKey(shipping);
            if(update_result <= 0){
                return ServerResponse.createServerResponseByError(ResponseCode.UPDATE_ADDRESS_FAIL.getStatus(),ResponseCode.UPDATE_ADDRESS_FAIL.getMsg());
            }
            return ServerResponse.createServerResponseBySuccess(Const.UPDATE_ADDRESS_SUCCESS);
        }
//        返回结果
        return ServerResponse.createServerResponseByError(ResponseCode.UPDATE_ADDRESS_FAIL.getStatus(),ResponseCode.UPDATE_ADDRESS_FAIL.getMsg());
    }

    @Override
    public ServerResponse select(Integer userId, Integer shippingId) {
//        非空校验
        if(shippingId == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        根据id和uid查询
        Shipping shipping = shippingMapper.selectByUidAndShippingId(userId,shippingId);
        if(shipping == null){
            return ServerResponse.createServerResponseByError(ResponseCode.SELECT_ADDRESS_FAIL.getStatus(),ResponseCode.SELECT_ADDRESS_FAIL.getMsg());
        }
//        返回结果
        return ServerResponse.createServerResponseBySuccess(null,shipping);
    }

    @Override
    public ServerResponse list(Integer userId, Integer pageNum, Integer pageSize) {
//        非空校验
        if(pageNum == null && pageSize == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        根据用户id进行查询相关的所有的地址内容
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUid(userId);
        if(shippingList == null){
            return ServerResponse.createServerResponseByError(ResponseCode.SELECT_ADDRESS_FAIL.getStatus(),ResponseCode.SELECT_ADDRESS_FAIL.getMsg());
        }
        PageInfo pageInfo = new PageInfo(shippingList);
//        返回分页结果
        return ServerResponse.createServerResponseBySuccess(null,pageInfo);
    }
}
