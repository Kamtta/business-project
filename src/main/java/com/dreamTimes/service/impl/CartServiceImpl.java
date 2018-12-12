package com.dreamTimes.service.impl;

import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.dao.CartMapper;
import com.dreamTimes.dao.ProductMapper;
import com.dreamTimes.pojo.Cart;
import com.dreamTimes.pojo.Product;
import com.dreamTimes.service.ICartService;
import com.dreamTimes.utils.DecimalUtils;
import com.dreamTimes.vo.CartProductVO;
import com.dreamTimes.vo.CartVO;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    CartMapper cartMapper;

    @Autowired
    ProductMapper productMapper;

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
        return ServerResponse.createServerResponseBySuccess(null,getCartVOLimit(userId));
    }

    @Override
    public ServerResponse list(Integer userId) {
        return ServerResponse.createServerResponseBySuccess(null,getCartVOLimit(userId));
    }

    @Override
    public ServerResponse update(Integer userId, Integer productId, Integer count) {
//        非空校验
        if(productId == null || count == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        更新购物车商品的数量
        Cart cart = cartMapper.findCartByUserIdAndProductId(userId,productId);
        if(cart != null){
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKey(cart);
        }
//        返回结果
        return ServerResponse.createServerResponseBySuccess(null,getCartVOLimit(userId));
    }

    @Override
    public ServerResponse delete_product(Integer userId, String productIds) {
//        参数的非空校验
        if(StringUtils.isBlank(productIds)){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        移除购物车中的某个商品
        String[] productId = productIds.split(",");
        Set<Integer> integerSet = Sets.newHashSet();
        if(productId != null && productId.length > 0){
            for(String str : productId){
                Integer id = Integer.parseInt(str);
                integerSet.add(id);
            }
        }
        int result = cartMapper.deleteByProductIds(userId,integerSet);
        if (result <= 0){
            return ServerResponse.createServerResponseByError(ResponseCode.DELETE_CART_FAIL.getStatus(),ResponseCode.DELETE_CART_FAIL.getMsg());
        }
//        返回结果
        return ServerResponse.createServerResponseBySuccess(null,getCartVOLimit(userId));
    }

    @Override
    public ServerResponse select(Integer userId, Integer productId) {
//        参数的非空校验
        if(productId == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        修改购物车商品的checked属性
        cartMapper.updateChecked(userId,productId,Const.SELECT_SUCCESS);
//        返回结果
        return ServerResponse.createServerResponseBySuccess(null,getCartVOLimit(userId));
    }

    @Override
    public ServerResponse un_select(Integer userId, Integer productId) {
        //        参数的非空校验
        if(productId == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        修改购物车商品的checked属性
        cartMapper.updateChecked(userId,productId,Const.SELECT_FAIL);
//        返回结果
        return ServerResponse.createServerResponseBySuccess(null,getCartVOLimit(userId));
    }

    @Override
    public ServerResponse get_cart_product_count(Integer userId) {
//        查询购物车中商品的数量
        int countResult = cartMapper.countProducts(userId);
//        返回结果
        return ServerResponse.createServerResponseBySuccess(null,countResult);
    }

    @Override
    public ServerResponse select_all(Integer userId) {
//        修改购物车商品的checked属性
        cartMapper.updateChecked(userId,null,Const.SELECT_SUCCESS);
//        返回结果
        return ServerResponse.createServerResponseBySuccess(null,getCartVOLimit(userId));
    }

    @Override
    public ServerResponse un_select_all(Integer userId) {
        //        修改购物车商品的checked属性
        cartMapper.updateChecked(userId,null,Const.SELECT_FAIL);
//        返回结果
        return ServerResponse.createServerResponseBySuccess(null,getCartVOLimit(userId));
    }

    public CartVO getCartVOLimit(Integer userId){
        CartVO cartVO = new CartVO();
//        根据userID查询商品信息
        List<Cart> cartList = cartMapper.findCartByUserId(userId);
        List<CartProductVO> cartProductVOList = Lists.newArrayList();
//        将cart转换成VO对象
        if(cartList != null && cartList.size() > 0){
            for (Cart cart:
                 cartList) {
                CartProductVO cartProductVO = assembleToCartVO(cart);
                cartProductVOList.add(cartProductVO);
            }
        }
//        计算总价
        BigDecimal sum = new BigDecimal("0");
        for (CartProductVO cartProductVO:
             cartProductVOList) {
            sum = sum.add(cartProductVO.getProductTotalPrice());
        }
        cartVO.setCartProductVoList(cartProductVOList);
        cartVO.setCartTotalPrice(sum);
//        判断购物车是否全选
        int result = cartMapper.checkedAll(userId);
        if(result > 0){
            cartVO.setAllChecked(false);
        }else {
            cartVO.setAllChecked(true);
        }
//        返回结果
        return cartVO;
    }

    public CartProductVO assembleToCartVO(Cart cart){
        CartProductVO cartProductVO = new CartProductVO();
        cartProductVO.setId(cart.getId());
        cartProductVO.setUserId(cart.getUserId());
        cartProductVO.setProductId(cart.getProductId());

        Product product = productMapper.selectByPrimaryKey(cart.getProductId());
        if(product != null){
            cartProductVO.setProductMainImage(product.getMainImage());
            cartProductVO.setProductName(product.getName());
            cartProductVO.setProductPrice(product.getPrice());
            cartProductVO.setProductStatus(product.getStatus());
            cartProductVO.setProductStock(product.getStock());
            cartProductVO.setProductSubtitle(product.getSubtitle());

            int stock = product.getStock();
            int limitProductCount = 0;
            if(stock >= cart.getQuantity()){
                limitProductCount = cart.getQuantity();
                cartProductVO.setLimitQuantity(ResponseCode.LIMIT_NUM_SUCCESS.getMsg());
            }else{
                limitProductCount = stock;
//                更新购物车中商品的数量
                Cart cart1 = new Cart();
                cart1.setProductId(cart.getProductId());
                cart1.setQuantity(stock);
                cart1.setId(cart.getId());
                cart1.setChecked(cart.getChecked());
                cart1.setUserId(cart.getUserId());
                cartMapper.updateByPrimaryKey(cart1);
                cartProductVO.setLimitQuantity(ResponseCode.LIMIT_NUM_FAIL.getMsg());
            }
            cartProductVO.setQuantity(limitProductCount);
        }
        cartProductVO.setProductChecked(cart.getChecked());
        cartProductVO.setProductTotalPrice(DecimalUtils.mul(product.getPrice().doubleValue(),cart.getQuantity().doubleValue()));
        return cartProductVO;
    }
}
