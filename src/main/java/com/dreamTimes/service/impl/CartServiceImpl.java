package com.dreamTimes.service.impl;

import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.dao.CartMapper;
import com.dreamTimes.pojo.Cart;
import com.dreamTimes.service.ICartService;
import com.dreamTimes.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    CartMapper cartMapper;

    @Override
    public ServerResponse add(Integer userId,Integer productId, Integer count) {
//        非空校验
        if(productId == null || count == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        添加商品信息
        Cart cart = cartMapper.findCartByUserIdAndProductId(userId,productId);
        if(cart == null){
//            添加
            Cart cart1 = new Cart();
            cart1.setProductId(productId);
            cart1.setUserId(userId);
            cart1.setQuantity(count);
            cart1.setChecked(ResponseCode.LIMIT_NUM_SUCCESS.getStatus());
            int result = cartMapper.insert(cart1);
            if(result <= 0){
                return ServerResponse.createServerResponseByError(ResponseCode.INSERT_CART_FAIL.getStatus(),ResponseCode.INSERT_CART_FAIL.getMsg());
            }
        }else{
//            更新
            Cart cart1 = new Cart();
            cart1.setId(cart.getId());
            cart1.setChecked(cart.getChecked());
            cart1.setQuantity(count);
            cart1.setUserId(userId);
            cart1.setProductId(productId);
            int update_result = cartMapper.updateByPrimaryKey(cart1);
            if(update_result <= 0){
                return ServerResponse.createServerResponseByError(ResponseCode.UPDATE_FAIL.getStatus(),ResponseCode.UPDATE_FAIL.getMsg());
            }
        }
        return null;
    }

    public CartVO getCartVOLimit(Integer userId){
        CartVO cartVO = new CartVO();
//        根据userID查询商品信息
        List<Cart> cartList = cartMapper.findCartByUserId(userId);
//        将cart转换成VO对象
        if(cartList != null && cartList.size() > 0){
            for (Cart cart:
                 cartList) {

            }
        }
//        计算总价
//        判断购物车是否全选
//        返回结果
        return null;
    }
}
