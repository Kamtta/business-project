package com.dreamTimes.service.impl;

import com.dreamTimes.commons.Const;
import com.dreamTimes.commons.ResponseCode;
import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.dao.*;
import com.dreamTimes.pojo.*;
import com.dreamTimes.service.IOrderService;
import com.dreamTimes.utils.DateUtils;
import com.dreamTimes.utils.DecimalUtils;
import com.dreamTimes.utils.PropertiesUtils;
import com.dreamTimes.vo.CartOrderItemVO;
import com.dreamTimes.vo.OrderItemVO;
import com.dreamTimes.vo.OrderVO;
import com.dreamTimes.vo.ShippingVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    CartMapper cartMapper;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ShippingMapper shippingMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Override
    public ServerResponse create(Integer userId, Integer shippingId) {
//        非空校验
        if(shippingId == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        根据用户id查询购物车的选中项
        List<Cart> cartList = cartMapper.findCheckedCartByUserId(userId);
        if(cartList == null || cartList.size() == 0){
            return ServerResponse.createServerResponseByError(ResponseCode.CART_EMPTY.getStatus(),ResponseCode.CART_EMPTY.getMsg());
        }
//        将购物车的东西变成orderItem
        ServerResponse serverResponse = assembleOrderItem(userId,cartList);
        if(!serverResponse.isSucess()){
            return serverResponse;
        }
        List<OrderItem> orderItemList = (List<OrderItem>) serverResponse.getData();
//        添加订单，并存储到数据库中
        BigDecimal orderTotalPrice = new BigDecimal("0");
        for (OrderItem orderItem:
             orderItemList) {
            orderTotalPrice = orderTotalPrice.add(orderItem.getTotalPrice());
        }
        ServerResponse serverResponse1 = createOrder(userId,shippingId,orderTotalPrice);
        if(!serverResponse1.isSucess()){
            return serverResponse1;
        }
        Order order = (Order)serverResponse1.getData();
//        添加订单明细表，将订单明细表添加到数据库
        for (OrderItem orderItem:
             orderItemList) {
            orderItem.setOrderNo(order.getOrderNo());
        }
        int result = orderItemMapper.insertBatch(orderItemList);
        if(result <= 0){
            return ServerResponse.createServerResponseByError(ResponseCode.INSERT_ORDERITEM_FAIL.getStatus(),ResponseCode.INSERT_ORDERITEM_FAIL.getMsg());
        }
//        扣除库存
        reduceProductStock(orderItemList);
//        删除提交的购物车项
        int delete_result = cartMapper.deleteBatch(cartList);
        if(delete_result <= 0){
            return ServerResponse.createServerResponseByError(ResponseCode.DELETE_FAIL.getStatus(),ResponseCode.DELETE_FAIL.getMsg());
        }
//        返回OrderVo
        Shipping shipping = shippingMapper.selectByUidAndShippingId(userId,shippingId);
        ShippingVO shippingVO = assembleshippingVO(shipping);
        List<OrderItemVO> orderItemVOList = Lists.newArrayList();
        for (OrderItem orderItem:
             orderItemList) {
            OrderItemVO orderItemVO = assembleOrderItemVO(orderItem);
            orderItemVOList.add(orderItemVO);
        }
        OrderVO orderVO = assembleOrderVO(order,shippingVO,orderItemVOList);
        return ServerResponse.createServerResponseBySuccess(null,orderVO);
    }

    @Override
    public ServerResponse get_order_cart_product(Integer userId) {
//        根据有用户id查询选中的购物车项
        List<Cart> cartList = cartMapper.findCheckedCartByUserId(userId);
//        转换成orderItem
        if(cartList == null || cartList.size() == 0){
            return ServerResponse.createServerResponseByError(ResponseCode.CART_EMPTY.getStatus(),ResponseCode.CART_EMPTY.getMsg());
        }
        ServerResponse serverResponse = assembleOrderItem(userId,cartList);
        if(!serverResponse.isSucess()){
            return serverResponse;
        }
        List<OrderItem> orderItemList = (List<OrderItem>)serverResponse.getData();
        List<OrderItemVO> orderItemVOList = Lists.newArrayList();
        BigDecimal orderTotalPrice = new BigDecimal("0");
        for (OrderItem orderItem:
             orderItemList) {
            orderTotalPrice = orderTotalPrice.add(orderItem.getTotalPrice());
            OrderItemVO orderItemVO = assembleOrderItemVO(orderItem);
            orderItemVOList.add(orderItemVO);
        }
//        封装vo
        CartOrderItemVO cartOrderItemVO = new CartOrderItemVO();
        cartOrderItemVO.setImageHost(PropertiesUtils.getKey("imagesHost"));
        cartOrderItemVO.setOrderItemVoList(orderItemVOList);
        cartOrderItemVO.setProductTotalPrice(orderTotalPrice);
//        返回结果
        return ServerResponse.createServerResponseBySuccess(null,cartOrderItemVO);
    }

    @Override
    public ServerResponse list(Integer userId, Integer pageNum, Integer pageSize) {
//        非空校验
        if(pageNum == null || pageSize == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        根据用户id获取全部订单
        List<Order> orderList = orderMapper.findAllOrderByUserId(userId);
        if(orderList == null || orderList.size()==0){
            return ServerResponse.createServerResponseByError(ResponseCode.ORDER_EMPTY.getStatus(),ResponseCode.ORDER_EMPTY.getMsg());
        }
//        根据订单号获取OrderVO对象
        PageHelper.startPage(pageNum,pageSize);
        List<OrderVO> orderVOList = Lists.newArrayList();
        for (Order order:
             orderList) {
            ServerResponse serverResponse = detail(userId,order.getOrderNo());
            if(!serverResponse.isSucess()){
               return serverResponse;
            }
            OrderVO orderVO = (OrderVO) serverResponse.getData();
            orderVOList.add(orderVO);
        }
//        返回结果
        PageInfo pageInfo = new PageInfo(orderVOList);
        return ServerResponse.createServerResponseBySuccess(null,pageInfo);
    }

    @Override
    public ServerResponse detail(Integer userId, Long orderNo) {
//        非空判断
        if(orderNo == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        根据订单号进行查询
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNo(orderNo);
        if(orderItemList == null || orderItemList.size() == 0){
            return ServerResponse.createServerResponseByError(ResponseCode.ORDER_EMPTY.getStatus(),ResponseCode.ORDER_EMPTY.getMsg());
        }
        Order order = orderMapper.selectOrderByOrderNO(userId,orderNo);
        Shipping shipping = shippingMapper.selectByUidAndShippingId(order.getUserId(),order.getShippingId());
        List<OrderItemVO> orderItemVOList = new ArrayList<>();
        for (OrderItem orderItem:
             orderItemList) {
            OrderItemVO orderItemVO = assembleOrderItemVO(orderItem);
            orderItemVOList.add(orderItemVO);
        }
        ShippingVO shippingVO = assembleshippingVO(shipping);
        OrderVO orderVO = assembleOrderVO(order,shippingVO,orderItemVOList);
//        返回结果
        return ServerResponse.createServerResponseBySuccess(null,orderVO);
    }

    @Override
    public ServerResponse cancel(Integer userId, Long orderNo) {
//        非空判断
        if(orderNo == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        根据orderNo和userId查询
        Order order = orderMapper.selectOrderByOrderNO(userId,orderNo);
        if(order == null){
            return ServerResponse.createServerResponseByError(ResponseCode.ORDER_EMPTY.getStatus(),ResponseCode.ORDER_EMPTY.getMsg());
        }
//        改变订单的状态
        if(order.getStatus() != ResponseCode.OrderStatus.NOT_PAYMENT.getCode()){
            return ServerResponse.createServerResponseByError(ResponseCode.ORDER_CANCEL_CANNOT.getStatus(),ResponseCode.ORDER_CANCEL_CANNOT.getMsg());
        }
        order.setStatus(ResponseCode.OrderStatus.ORDER_CANCEL.getCode());
        int result = orderMapper.updateByPrimaryKey(order);
        if(result <= 0){
            return ServerResponse.createServerResponseByError(ResponseCode.ORDER_CANCEL_FAIL.getStatus(),ResponseCode.ORDER_CANCEL_FAIL.getMsg());
        }
//        返回结果
        return ServerResponse.createServerResponseBySuccess();
    }

    /**
     * 后台接口实现
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse listOrders(Integer pageNum, Integer pageSize) {
//       非空校验
        if(pageNum == null || pageSize == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//       获取所有用户的订单信息
        List<Order> orderList = orderMapper.selectAll();
        if(orderList == null || orderList.size() == 0){
            return ServerResponse.createServerResponseByError(ResponseCode.ORDER_EMPTY.getStatus(),ResponseCode.ORDER_EMPTY.getMsg());
        }
        PageHelper.startPage(pageNum,pageSize);
        List<OrderVO> orderVOList = new ArrayList<>();
        for (Order order:
             orderList) {
           ServerResponse serverResponse = list(order.getUserId(),pageNum,pageSize);
           if(!serverResponse.isSucess()){
                return serverResponse;
           }
            orderVOList.addAll(((PageInfo)serverResponse.getData()).getList());
        }
//        返回结果
        PageInfo pageInfo = new PageInfo(orderVOList);
        return ServerResponse.createServerResponseBySuccess(null,pageInfo);
    }

    @Override
    public ServerResponse search(Long orderNo,Integer pageNum,Integer pageSize) {
//        非空校验
        if(orderNo == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        通过订单号进行查询
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList = orderMapper.findOrderByOrderNO(orderNo);
        if(orderList == null || orderList.size() == 0){
            return ServerResponse.createServerResponseByError(ResponseCode.NOT_FOUND_ORDER.getStatus(),ResponseCode.NOT_FOUND_ORDER.getMsg());
        }
        List<OrderVO> orderVOList = new ArrayList<>();
        for (Order order:
             orderList) {
           ServerResponse serverResponse = detail(order.getUserId(),order.getOrderNo());
           if (!serverResponse.isSucess()){
               return serverResponse;
           }
           OrderVO orderVO = (OrderVO) serverResponse.getData();
           orderVOList.add(orderVO);
        }

//        返回结果
        PageInfo pageInfo = new PageInfo(orderVOList);
        return ServerResponse.createServerResponseBySuccess(null,pageInfo);
    }

    @Override
    public ServerResponse orderDetail(Long orderNo) {
        //        非空判断
        if(orderNo == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
        return detail(null,orderNo);
    }

    @Override
    public ServerResponse send_goods(Long orderNo) {
//        非空判断
        if(orderNo == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
//        根据订单号进行查询
        Order order = orderMapper.selectOrderByOrderNO(null,orderNo);
        if(order == null){
            return ServerResponse.createServerResponseByError(ResponseCode.NOT_FOUND_ORDER.getStatus(),ResponseCode.NOT_FOUND_ORDER.getMsg());
        }
//        修改订单的状态
        order.setStatus(ResponseCode.OrderStatus.SENT_PRODUCT.getCode());
        int result = orderMapper.updateByPrimaryKey(order);
        if(result <= 0){
            return ServerResponse.createServerResponseByError(ResponseCode.SENT_ERROR.getStatus(),ResponseCode.SENT_ERROR.getMsg());
        }
//        返回结果
        return ServerResponse.createServerResponseBySuccess(null,Const.SENT_SUCCESS);
    }


    /**
     * 将购物车的商品添加到订单项
     */
    public ServerResponse assembleOrderItem(Integer userId,List<Cart> cartList){
        List<OrderItem> orderItemList = Lists.newArrayList();
        for (Cart cart:
             cartList) {
            OrderItem orderItem = new OrderItem();
            Product product = productMapper.selectByPrimaryKey(cart.getProductId());
            if(product == null){
                return ServerResponse.createServerResponseByError(ResponseCode.NOT_SUCH_PRODUCT.getStatus(),ResponseCode.NOT_SUCH_PRODUCT.getMsg());
            }
            if(product.getStock() < cart.getQuantity()){
                return ServerResponse.createServerResponseByError(ResponseCode.STOCK_NOT_ENOUGH.getStatus(),ResponseCode.STOCK_NOT_ENOUGH.getMsg());
            }
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setProductId(product.getId());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setProductName(product.getName());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setTotalPrice(DecimalUtils.mul(product.getPrice().doubleValue(),cart.getQuantity().doubleValue()));
            orderItem.setUserId(userId);
            orderItemList.add(orderItem);
        }
        return ServerResponse.createServerResponseBySuccess(null,orderItemList);
    }


    /**
     * 创建订单
     */
    private ServerResponse createOrder(Integer userId, Integer shippingId, BigDecimal orderTotalPrice){
        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setPayment(orderTotalPrice);
        order.setPaymentType(ResponseCode.OrderStatus.ONLINE_PAY.getCode());
        Shipping shipping = shippingMapper.selectByUidAndShippingId(userId,shippingId);
        if(shipping == null){
            return ServerResponse.createServerResponseByError(ResponseCode.PLEASE_ADD_ADDRESS.getStatus(),ResponseCode.PLEASE_ADD_ADDRESS.getMsg());
        }
        order.setPostage(Integer.valueOf(shipping.getReceiverZip()));
        order.setShippingId(shippingId);
        order.setUserId(userId);
        order.setStatus(ResponseCode.OrderStatus.NOT_PAYMENT.getCode());
//        添加订单到数据库
        int result = orderMapper.insert(order);
        if(result <= 0){
            return ServerResponse.createServerResponseByError(ResponseCode.INSERT_ORDER_FAIL.getStatus(),ResponseCode.INSERT_ORDER_FAIL.getMsg());
        }
        return ServerResponse.createServerResponseBySuccess(null,order);
    }


    /**
     * 生成订单编号
     * @return
     */
    private Long generateOrderNo(){
        return System.currentTimeMillis()+new Random().nextInt(100);
    }

    /**
     * 扣除库存
     */
    private void reduceProductStock(List<OrderItem> orderItemList){
        for (OrderItem orderItem:
             orderItemList) {
            Integer productId = orderItem.getProductId();
            Product product = productMapper.selectByPrimaryKey(productId);
            Integer stock = product.getStock();
            product.setStock(stock-orderItem.getQuantity());
            productMapper.updateByPrimaryKey(product);
        }
    }

    /**
     * 转换成ShippingVo
     */
    private ShippingVO assembleshippingVO(Shipping shipping){
        ShippingVO shippingVO = new ShippingVO();
        shippingVO.setReceiverAddress(shipping.getReceiverAddress());
        shippingVO.setReceiverCity(shipping.getReceiverCity());
        shippingVO.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVO.setReceiverMobile(shipping.getReceiverMobile());
        shippingVO.setReceiverName(shipping.getReceiverName());
        shippingVO.setReceiverPhone(shipping.getReceiverPhone());
        shippingVO.setReceiverProvince(shipping.getReceiverProvince());
        shippingVO.setReceiverZip(shipping.getReceiverZip());
        return shippingVO;
    }


    /**
     * 转换成OrderItemVO
     */
    private OrderItemVO assembleOrderItemVO(OrderItem orderItem){
        OrderItemVO orderItemVO = new OrderItemVO();
        orderItemVO.setCreateTime(DateUtils.dateToStr(orderItem.getCreateTime()));
        orderItemVO.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVO.setOrderNo(orderItem.getOrderNo());
        orderItemVO.setProductId(orderItem.getProductId());
        orderItemVO.setProductImage(orderItem.getProductImage());
        orderItemVO.setProductName(orderItem.getProductName());
        orderItemVO.setQuantity(orderItem.getQuantity());
        orderItemVO.setTotalPrice(orderItem.getTotalPrice());
        return orderItemVO;
    }

    private OrderVO assembleOrderVO(Order order,ShippingVO shippingVO,List<OrderItemVO> orderItemVOList){
        OrderVO orderVO = new OrderVO();
        orderVO.setCloseTime(DateUtils.dateToStr(order.getCloseTime()));
        orderVO.setCreateTime(DateUtils.dateToStr(order.getCreateTime()));
        orderVO.setEndTime(DateUtils.dateToStr(order.getEndTime()));
        orderVO.setImageHost(PropertiesUtils.getKey("imagesHost"));
        orderVO.setOrderItemVoList(orderItemVOList);
        orderVO.setOrderNo(order.getOrderNo());
        orderVO.setPayment(order.getPayment());
        orderVO.setPaymentTime(DateUtils.dateToStr(order.getPaymentTime()));
        orderVO.setPaymentType(order.getPaymentType());
        orderVO.setPaymentTypeDesc(ResponseCode.OrderStatus.ONLINE_PAY.getDesc());
        orderVO.setPostage(order.getPostage());
        orderVO.setReceiverName(shippingVO.getReceiverName());
        orderVO.setSendTime(DateUtils.dateToStr(order.getSendTime()));
        orderVO.setShippingId(order.getShippingId());
        orderVO.setShippingVo(shippingVO);
        orderVO.setStatus(order.getStatus());
        ResponseCode.OrderStatus orderStatus = ResponseCode.OrderStatus.codeOf(order.getStatus());
        if(orderStatus != null){
            orderVO.setStatusDesc(orderStatus.getDesc());
        }
        return orderVO;
    }
}
