package com.dreamTimes.controller.manage;


import com.dreamTimes.commons.ServerResponse;
import com.dreamTimes.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/manage/order/")
public class OrderManageController {

    @Autowired
    IOrderService orderService;


    /**
     * 订单list
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list.do")
    public ServerResponse list(@RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
            return orderService.listOrders(pageNum,pageSize);
    }


    /**
     * 按订单号进行查询
     * @return
     */
    @RequestMapping(value = "search.do")
    public ServerResponse search(@RequestParam(value = "orderNo") Long orderNo,
                                 @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                                 @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
        return orderService.search(orderNo,pageNum,pageSize);
    }


    /**
     * 订单详情detail
     * @return
     */
    @RequestMapping(value = "detail/{orderNo}")
    public ServerResponse detail(@PathVariable("orderNo") Long orderNo){
            return orderService.orderDetail(orderNo);
    }


    /**
     * 订单发货
     * @return
     */
    @RequestMapping(value = "send_goods/{orderNo}")
    public ServerResponse send_goods(@PathVariable("orderNo") Long orderNo){
            return orderService.send_goods(orderNo);
    }
}
